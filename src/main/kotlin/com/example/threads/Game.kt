package com.example.threads

import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11C
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import org.lwjgl.system.Platform
import java.util.*
import java.util.concurrent.CountDownLatch
import kotlin.math.abs
import kotlin.math.sin

/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 */


/**
 * GLFW demo that showcases rendering to multiple windows from multiple threads. Ported from the GLFW
 * [threads](https://github.com/glfw/glfw/blob/master/tests/threads.c) test.
 *
 * @author Brian Matzon <brian></brian>@matzon.dk>
 */
object Threads {
    private val titles = arrayOf("Red", "Green", "Blue")
    private val rgb = arrayOf(floatArrayOf(1f, 0f, 0f, 0f), floatArrayOf(0f, 1f, 0f, 0f), floatArrayOf(0f, 0f, 1f, 0f))

    @JvmStatic
    fun main(args: Array<String>) {
        GLFWErrorCallback.createPrint().set()
        check(GLFW.glfwInit()) { "Failed to initialize GLFW." }
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE)
        GLFW.glfwWindowHint(GLFW.GLFW_SCALE_TO_MONITOR, GLFW.GLFW_TRUE)
        if (Platform.get() === Platform.MACOSX) {
            GLFW.glfwWindowHint(GLFW.GLFW_COCOA_RETINA_FRAMEBUFFER, GLFW.GLFW_FALSE)
        }
        var scaleX: Int
        MemoryStack.stackPush().use { s ->
            val px = s.mallocFloat(1)
            GLFW.glfwGetMonitorContentScale(GLFW.glfwGetPrimaryMonitor(), px, null)
            scaleX = px[0].toInt()
        }
        val quit = CountDownLatch(1)
        val threads = arrayOfNulls<GLFWThread>(titles.size)
        for (i in titles.indices) {
            val window = GLFW.glfwCreateWindow(200, 200, titles[i], MemoryUtil.NULL, MemoryUtil.NULL)
            check(window != MemoryUtil.NULL) { "Failed to create GLFW window." }
            GLFW.glfwSetKeyCallback(
                window
            ) { windowHnd: Long, key: Int, scancode: Int, action: Int, mods: Int ->
                if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
                    GLFW.glfwSetWindowShouldClose(windowHnd, true)
                }
            }
            GLFW.glfwSetWindowPos(window, 200 + i * (200 * scaleX + 50), 200)
            GLFW.glfwShowWindow(window)
            threads[i] = GLFWThread(window, i, quit)
            threads[i]!!.start()
        }
        out@ while (true) {
            GLFW.glfwWaitEvents()
            for (i in titles.indices) {
                if (GLFW.glfwWindowShouldClose(threads[i]!!.window)) {
                    quit.countDown()
                    break@out
                }
            }
        }
        for (i in threads.indices) {
            try {
                threads[i]!!.join()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        for (i in threads.indices) {
            Callbacks.glfwFreeCallbacks(threads[i]!!.window)
            GLFW.glfwDestroyWindow(threads[i]!!.window)
        }
        GLFW.glfwTerminate()
        Objects.requireNonNull(GLFW.glfwSetErrorCallback(null))?.free()
    }

    private class GLFWThread internal constructor(val window: Long, val index: Int, quit: CountDownLatch) : Thread() {
        val r: Float = rgb[index][0]
        val g: Float = rgb[index][1]
        val b: Float = rgb[index][2]
        var quit: CountDownLatch

        init {
            println("GLFWThread: window:$window, rgb: ($r, $g, $b)")
            this.quit = quit
        }

        override fun run() {
            GLFW.glfwMakeContextCurrent(window)
            GL.createCapabilities()
            GLFW.glfwSwapInterval(1)
            while (quit.count != 0L) {
                val v = abs(sin(GLFW.glfwGetTime() * 2f)).toFloat()
                GL11C.glClearColor(r * v, g * v, b * v, 0f)
                GL11C.glClear(GL11C.GL_COLOR_BUFFER_BIT)
                GLFW.glfwSwapBuffers(window)
            }
            GL.setCapabilities(null)
        }
    }
}