package com.example.my_engine.graphic.render

import com.example.my_engine.graphic.*
import com.example.my_engine.graphic.imgui.ImGuiMesh
import com.example.my_engine.scene.Scene
import com.example.tutorial.math.Vector2f
import imgui.ImDrawData
import imgui.ImFontAtlas
import imgui.ImGui
import imgui.ImGuiIO
import imgui.flag.ImGuiKey
import imgui.type.ImInt
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.*

class ImGuiRenderer(val window: Window) {

    private val guiMesh: ImGuiMesh
    private val scale = Vector2f()
    private val texture: Texture
    private val program: ShaderProgram
    private val scaleUniform: Uniform



    init {
        val vertShader = Shader.loadShader(ShaderType.Vertex, "shaders/imgui.vert")
        val fragShader = Shader.loadShader(ShaderType.Fragment, "shaders/imgui.frag")
        program = ShaderProgram()

        program.attachShader(vertShader)
        program.attachShader(fragShader)
        program.link()

        // after we linked the program we don't need the shaders
        program.detachShader(vertShader)
        program.detachShader(fragShader)
        vertShader.delete()
        fragShader.delete()

        // setup uniforms
        scaleUniform = program.uniformLocationOf("scale")

        // create ui resources
        ImGui.createContext()

        val imGuiIO: ImGuiIO = ImGui.getIO()
        imGuiIO.iniFilename = null
        window.size.let { (width, height) ->
            imGuiIO.setDisplaySize(width.toFloat(), height.toFloat())
        }

        val fontAtlas: ImFontAtlas = ImGui.getIO().fonts
        val width = ImInt()
        val height = ImInt()
        val buf = fontAtlas.getTexDataAsRGBA32(width, height)
        texture = Texture.createTexture(width.get(), height.get(), buf, TexturePath("ImGui"))

        guiMesh = ImGuiMesh()

        // setup key callback
        setupKeyCallBack(window)
    }

    private fun setupKeyCallBack(window: Window) {
        val io = ImGui.getIO()
        io.setKeyMap(ImGuiKey.Tab, GLFW_KEY_TAB)
        io.setKeyMap(ImGuiKey.LeftArrow, GLFW_KEY_LEFT)
        io.setKeyMap(ImGuiKey.RightArrow, GLFW_KEY_RIGHT)
        io.setKeyMap(ImGuiKey.UpArrow, GLFW_KEY_UP)
        io.setKeyMap(ImGuiKey.DownArrow, GLFW_KEY_DOWN)
        io.setKeyMap(ImGuiKey.PageUp, GLFW_KEY_PAGE_UP)
        io.setKeyMap(ImGuiKey.PageDown, GLFW_KEY_PAGE_DOWN)
        io.setKeyMap(ImGuiKey.Home, GLFW_KEY_HOME)
        io.setKeyMap(ImGuiKey.End, GLFW_KEY_END)
        io.setKeyMap(ImGuiKey.Insert, GLFW_KEY_INSERT)
        io.setKeyMap(ImGuiKey.Delete, GLFW_KEY_DELETE)
        io.setKeyMap(ImGuiKey.Backspace, GLFW_KEY_BACKSPACE)
        io.setKeyMap(ImGuiKey.Space, GLFW_KEY_SPACE)
        io.setKeyMap(ImGuiKey.Enter, GLFW_KEY_ENTER)
        io.setKeyMap(ImGuiKey.Escape, GLFW_KEY_ESCAPE)
        io.setKeyMap(ImGuiKey.KeyPadEnter, GLFW_KEY_KP_ENTER)

        window.keyListeners[KEYS_KEY] = keyCallback@ { key: Int, scancode: Int, action: Int, mods: Int ->
            if (io.wantCaptureKeyboard) {
                return@keyCallback
            }

            if (action == GLFW_PRESS) {
                io.setKeysDown(key, true)
                io.addInputCharacter(key)
            } else if (action == GLFW_RELEASE) {
                io.setKeysDown(key, false)
            }
            io.keyCtrl = io.getKeysDown(GLFW_KEY_LEFT_CONTROL) || io.getKeysDown(GLFW_KEY_RIGHT_CONTROL)
            io.keyShift = io.getKeysDown(GLFW_KEY_LEFT_SHIFT) || io.getKeysDown(GLFW_KEY_RIGHT_SHIFT)
            io.keyAlt = io.getKeysDown(GLFW_KEY_LEFT_ALT) || io.getKeysDown(GLFW_KEY_RIGHT_ALT)
            io.keySuper = io.getKeysDown(GLFW_KEY_LEFT_SUPER) || io.getKeysDown(GLFW_KEY_RIGHT_SUPER)
        }
    }

    fun delete() {
        window.keyListeners.remove(KEYS_KEY)

        guiMesh.delete()
        program.unbind()
        program.delete()
        texture.delete()
    }

    fun render(scene: Scene) {
        scene.imGuiInstance?.let { guiInstance ->
            guiInstance.drawGui()

            program.bind()

            GL11.glEnable(GL11.GL_BLEND)
            GL14.glBlendEquation(GL14.GL_FUNC_ADD)
            GL14.glBlendFuncSeparate(GL14.GL_SRC_ALPHA, GL14.GL_ONE_MINUS_SRC_ALPHA, GL14.GL_ONE, GL14.GL_ONE_MINUS_SRC_ALPHA)
            GL11.glDisable(GL11.GL_DEPTH_TEST)
            GL11.glDisable(GL11.GL_CULL_FACE)

            guiMesh.vao.bind()
            guiMesh.verticesVbo.bind()
            guiMesh.indicesVbo.bind(GL15.GL_ELEMENT_ARRAY_BUFFER)

            val io = ImGui.getIO()
            scale.x = 2.0f / io.displaySizeX
            scale.y = -2.0f / io.displaySizeY
            program.setUniformData(scaleUniform, scale)

            val drawData = ImGui.getDrawData()
            for (i in 0 until drawData.cmdListsCount) {
                guiMesh.verticesVbo.uploadData(data = drawData.getCmdListVtxBufferData(i), usage = GL15.GL_STREAM_DRAW)
                guiMesh.indicesVbo.uploadElementArrayData(data = drawData.getCmdListIdxBufferData(i), usage = GL15.GL_STREAM_DRAW)

                for (j in 0 until drawData.getCmdListCmdBufferSize(i)) {
                    val elementCount = drawData.getCmdListCmdBufferElemCount(i, j)
                    val idxBufferOffset = drawData.getCmdListCmdBufferIdxOffset(i, j)
                    val indices = idxBufferOffset * ImDrawData.SIZEOF_IM_DRAW_IDX

                    texture.bind()
                    GL11.glDrawElements(GL11.GL_TRIANGLES, elementCount, GL11.GL_UNSIGNED_SHORT, indices.toLong())
                }
            }

            GL11.glEnable(GL11.GL_DEPTH_TEST)
            GL11.glEnable(GL11.GL_CULL_FACE)
            GL11.glDisable(GL11.GL_BLEND)
        }
    }

    fun resize() {
        val (width, height) = window.size

        val io = ImGui.getIO()
        io.setDisplaySize(width.toFloat(), height.toFloat())
    }


    companion object {
        private const val KEYS_KEY = "ImGui"
    }
}