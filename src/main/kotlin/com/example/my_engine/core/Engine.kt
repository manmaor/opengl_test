package com.example.my_engine.core

import com.example.my_engine.graphic.Window
import com.example.my_engine.graphic.render.Render
import com.example.my_engine.scene.Scene
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL11

abstract class Engine {

    var running = false
        private set

    val window: Window
    val render: Render
    val scene: Scene
    val mouse: Mouse

    init {
        GLFWErrorCallback.createPrint(System.err).set()

        if (!GLFW.glfwInit()) {
            throw IllegalStateException("Unable to initialize GLFW")
        }

        window = Window(1280, 720, "Rename me") {
            resize()
        }
        render = Render(window)
        scene = Scene(window)
        mouse = Mouse(window)

        println("----------------------------")
        println("OpenGL Version : " + GL11.glGetString(GL11.GL_VERSION))
        println("OpenGL Max Texture Size : " + GL11.glGetInteger(GL11.GL_MAX_TEXTURE_SIZE))
        println("OpenGL Vendor : " + GL11.glGetString(GL11.GL_VENDOR))
        println("OpenGL Renderer : " + GL11.glGetString(GL11.GL_RENDERER))
        println("OpenGL Extensions supported by your card : ")
        val extensions = GL11.glGetString(GL11.GL_EXTENSIONS)
        println(extensions)

    }

    private fun resize() {
        scene.resize()
        render.resize()
    }

    private fun run() {
        running = true

        val timeU = SEC_IN_MILLIS / TARGET_UPS

        var initialTime = System.currentTimeMillis()
        var deltaUpdate = 0f
        var initialUpdateTime = initialTime

        while (running && !window.isClosing()) {
            window.clear()

            val now = System.currentTimeMillis()
            val deltaTime = now - initialTime

            deltaUpdate += deltaTime / timeU

            mouse.input()
            val inputConsumed = scene.imGuiInstance?.handleGuiInput(scene, window) ?: false
            if (!inputConsumed) {
                input(deltaTime)
            }

//            if (deltaUpdate >= 1) {
//                val updateDeltaTime = now - initialUpdateTime
            update(deltaTime /*updateDeltaTime*/)
//                initialUpdateTime = now
//                deltaUpdate--
//            }

            render.render(scene)

            window.update()

            initialTime = now
        }

        deleteEngine()
    }


    fun start() {
        running = true
        run()
    }

    fun stop() {
        running = false
    }

    private fun deleteEngine() {
        delete()

        render.delete()
        window.dispose()
    }

    abstract fun input(delta: Long)
    abstract fun update(delta: Long)
    abstract fun delete()


    companion object {
        const val TARGET_UPS = 30

        private const val SEC_IN_MILLIS = 1000f

    }
}