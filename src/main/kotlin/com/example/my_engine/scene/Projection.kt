package com.example.my_engine.scene

import com.example.my_engine.graphic.Window
import org.joml.Matrix4f

class Projection(
    val window: Window,
    val projectionMatrix: Matrix4f = Matrix4f()
) {
    init {
        update()
    }

    fun update() {
        val (width, height) = window.size
        updateProjectionMatrix(width, height)
    }

    fun updateProjectionMatrix(width: Int, height: Int) {
        projectionMatrix.setPerspective(FOV, width.toFloat() / height.toFloat(), Z_NEAR, Z_FAR)
    }

    companion object {
        const val FOV = 70f
        const val Z_NEAR = 0.1f
        const val Z_FAR = 1000f
    }
}