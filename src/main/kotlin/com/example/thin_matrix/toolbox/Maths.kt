package com.example.thin_matrix.toolbox

import org.joml.Matrix4f
import org.joml.Vector3f

object Maths {
    fun createTransformationMatrix(
        translation: Vector3f,
        rx: Float,
        ry: Float,
        rz: Float,
        scale: Float
    ) = Matrix4f().apply {
        translate(translation, this)
        rotate(Math.toRadians(rx.toDouble()).toFloat(), Vector3f(1f,0f,0f), this)
        rotate(Math.toRadians(ry.toDouble()).toFloat(), Vector3f(0f,1f,0f), this)
        rotate(Math.toRadians(rz.toDouble()).toFloat(), Vector3f(0f,0f,1f), this)
        scale(Vector3f(scale, scale, scale), this)
    }

}