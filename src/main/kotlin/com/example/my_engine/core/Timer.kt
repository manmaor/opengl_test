package com.example.my_engine.core

import org.lwjgl.glfw.GLFW.glfwGetTime

class Timer {

    private var timeCount: Double = 0.0
    private var fps: Int = 0
    private var fpsCount: Int = 0
    private var ups: Int = 0
    private var upsCount: Int = 0

    fun init() {
        lastLoopTime = this.time
    }

    var lastLoopTime: Double = 0.0
        private set

    val time: Double
        get() = glfwGetTime()

    val delta: Double
        get() {
            val time = this.time
            val delta = time - lastLoopTime
            lastLoopTime = time
            timeCount + delta
            return delta
        }

    fun updateFPS() {
        fpsCount += 1
    }

    fun updateUPS() {
        upsCount += 1
    }

    fun update() {
        if (timeCount > 1) {
            fps = fpsCount
            fpsCount = 0

            ups = upsCount
            upsCount = 0

            timeCount -= 1
        }
    }


}