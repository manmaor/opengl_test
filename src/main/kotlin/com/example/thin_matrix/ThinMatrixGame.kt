package com.example.thin_matrix

import com.example.thin_matrix.renderer.Loader
import com.example.thin_matrix.renderer.Renderer
import com.example.thin_matrix.shaders.StaticShader
import com.example.spookycopengl.graphic.Window
import org.lwjgl.Version
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL11


class ThinMatrixGame {

    // VAO - vertex array object [per 3d model] holds 16 vbo
    // VBO - vertex buffer object - per buffer

    private lateinit var window: Window
    private lateinit var renderer: Renderer
    private lateinit var shader: StaticShader

    fun start() {
        println("Hello LWJGL ${Version.getVersion()}!")

        init()

        loop()
        dispose()
    }

    private fun init() {

        GLFWErrorCallback.createPrint(System.err).set()

        if (!GLFW.glfwInit()) {
            throw IllegalStateException("Unable to initialize GLFW")
        }

        window = Window(1280, 720, "Chapter2 - program1")
//        window.location = Pair(200, 200)

        renderer = Renderer()
        shader = StaticShader()
    }


    private fun loop() {

        val vertices = arrayListOf(
            -.5f, .5f, 0f,
            -.5f, -.5f, 0f,
            .5f, -.5f, 0f,
            .5f, .5f, 0f
        ).toFloatArray()

        val indices = arrayListOf(
            0,1,3,
            3,1,2
        ).toIntArray()
        val model = Loader.loadToVAO(vertices, indices)


        println("----------------------------")
        println("OpenGL Version : " + GL11.glGetString(GL11.GL_VERSION))
        println("OpenGL Max Texture Size : " + GL11.glGetInteger(GL11.GL_MAX_TEXTURE_SIZE))
        println("OpenGL Vendor : " + GL11.glGetString(GL11.GL_VENDOR))
        println("OpenGL Renderer : " + GL11.glGetString(GL11.GL_RENDERER))
        println("OpenGL Extensions supported by your card : ")
        val extensions = GL11.glGetString(GL11.GL_EXTENSIONS)
        println(extensions)

        while (!window.isClosing()) {
            renderer.prepare()
//            window.clear() // Called in the renderer

//            val delta = glfwGetTimerValue()
            // input
            // update
            // render
            shader.start()

            renderer.render(model)

            shader.stop()

            window.update()
        }
    }

    private fun drawQuad(sp: Float) {

        GL11.glBegin(GL11.GL_QUADS)
        run {
            GL11.glVertex3f(-sp, -sp, 0.0f)
            GL11.glVertex3f(sp, -sp, 0.0f)
            GL11.glVertex3f(sp, sp, 0.0f)
            GL11.glVertex3f(-sp, sp, 0.0f)
        }
        GL11.glBegin(GL11.GL_QUADS)
    }

    private fun dispose() {
        shader.clean()
        Loader.clean()
        window.dispose()
    }
}