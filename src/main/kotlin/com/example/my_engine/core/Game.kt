package com.example.my_engine.core

import com.example.my_engine.core.logger.Log
import com.example.my_engine.graphic.Window
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.glfwTerminate
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL

class Game() {

    private var running: Boolean = false
    private lateinit var window: Window
    private val timer = Timer()
    // TODO: renderer
    // TODO: state?

    fun start() {
        init()
        loop()
        dispose()
    }

    private fun init() {
        GLFWErrorCallback.createPrint(System.err).set()

        if (!GLFW.glfwInit()) {
            throw IllegalStateException("Unable to initialize GLFW")
        }

        window = Window(1280, 1024, "Hello World!", true)

        timer.init()
        // TODO: renderer.init()
        // TODO: initStates()

        running = true
    }

    private fun initStates() {
        throw NotImplementedError()
    }

    private fun loop() {

        var delta: Double = .0

        while (running) {
            if (window.isClosing()) {
                running = false
            }

            delta = timer.delta

            input()
            update(delta)
            timer.updateUPS()

            render()
            timer.updateFPS()

            timer.update()

            /* TODO: Draw FPS, UPS and Context version */
//        val height: Int = renderer.getDebugTextHeight("Context")
//        renderer.drawDebugText("FPS: " + timer.getFPS() + " | UPS: " + timer.getUPS(), 5, 5 + height)
//        renderer.drawDebugText("Context: " + if (isDefaultContext()) "3.2 core" else "2.1", 5, 5)

            window.update()

            if (!window.vSync) {
                sync(TARGET_FPS)
            }
        }
    }

    fun input() {
        // TODO: state.input()
    }

    fun update(delta: Double) {
        // TODO: state.update(delta)
    }

    fun render(alpha: Float? = null) {
        // TODO: state.render()
    }

    /**
     * Synchronizes the game at specified frames per second.
     *
     * @param fps Frames per second
     */
    fun sync(fps: Int) {
        val lastLoopTime = timer.lastLoopTime
        var now = timer.time
        val targetTime = 1 / fps

        while (now - lastLoopTime < targetTime) {
            Thread.yield()

            /* This is optional if you want your game to stop consuming too much
             * CPU but you will loose some accuracy because Thread.sleep(1)
             * could sleep longer than 1 millisecond */
            try {
                Thread.sleep(1)
            } catch (e: Exception) {
                Log.d(Game::class.java.simpleName, e.stackTraceToString())
            }

            now = timer.time
        }
    }

    private fun dispose() {
        GLFW.glfwSetErrorCallback(null)?.free()

        // TODO: renderer.dispose
        // TODO: state.dispose
        window.dispose()

        glfwTerminate()
    }

    companion object {
        const val TARGET_FPS: Int = 90

        fun isDefaultContext(): Boolean = GL.getCapabilities().OpenGL32
    }
}