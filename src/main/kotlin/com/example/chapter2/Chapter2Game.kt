package com.example.chapter2

import com.example.chapter1.GLVersion
import com.example.chapter2.renderer.Loader
import com.example.chapter2.renderer.Renderer
import com.example.chapter2.shaders.StaticShader
import com.example.spookycopengl.graphic.Window
import org.lwjgl.Version
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11.*


class Chapter2Game {

    // VAO - vertex array object [per 3d model] holds 16 vbo
    // VBO - vertex buffer object - per buffer

    private lateinit var window: Window
    private lateinit var renderer: Renderer
    private lateinit var shader: StaticShader

    fun start() {
        println("Hello LWJGL ${Version.getVersion()}!")

        init()

        val a = GLVersion()

        loop()
        dispose()
    }

    private fun init() {

        GLFWErrorCallback.createPrint(System.err).set()

        if (!org.lwjgl.glfw.GLFW.glfwInit()) {
            throw IllegalStateException("Unable to initialize GLFW")
        }

        window = Window(1280, 720, "Chapter2 - program1")
//        window.location = Pair(200, 200)

        renderer = Renderer(window)
        shader = StaticShader()
    }


    private fun loop() {

//        glEnable(GL_BLEND)
//        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)

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

        val extensions = GL11.glGetString(GL11.GL_EXTENSIONS)
        println(extensions)

        while (!window.isClosing()) {
            renderer.prepare()
//            window.clear() // Called in the renderer

//            val delta = glfwGetTimerValue()
            // input
            // update
            // render
//            shader.start()
            renderer.render(model)
//            shader.stop()

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