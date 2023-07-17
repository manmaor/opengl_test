package com.example.chapter2

import com.example.chapter2.renderer.Loader
import com.example.chapter2.renderer.Renderer
import com.example.chapter2.shaders.ShaderProgram
import com.example.chapter2.shaders.StaticShader
import com.example.spookycopengl.graphic.Window
import org.lwjgl.Version
import org.lwjgl.glfw.GLFWErrorCallback

class Chapter2Game {

    // VAO - vertex array object [per 3d model] holds 16 vbo
    // VBO - vertex buffer object - per buffer

    private lateinit var window: Window
    private lateinit var renderer: Renderer

    fun start() {
        println("Hello LWJGL ${Version.getVersion()}!")

        init()
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

        while (!window.isClosing()) {
            renderer.prepare()
//            window.clear() // Called in the renderer

//            val delta = glfwGetTimerValue()
            // input
            // update
            // render
            StaticShader.start()
            renderer.render(model)
            StaticShader.stop()


            window.update()
        }
    }

    private fun dispose() {
        StaticShader.clean()
        Loader.clean()
        window.dispose()
    }
}