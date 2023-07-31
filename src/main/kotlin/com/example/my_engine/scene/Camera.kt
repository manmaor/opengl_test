package com.example.my_engine.scene

import org.joml.Math
import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f

class Camera(
    val direction: Vector3f = Vector3f(),
    val position: Vector3f = Vector3f(),
    val right: Vector3f = Vector3f(),
    val rotation: Vector2f = Vector2f(),
    val up: Vector3f = Vector3f()
) {

    val viewMatrix: Matrix4f
        get() = Matrix4f()
            .rotateX(rotation.x)
            .rotateY(rotation.y)
            .translate(-position.x, -position.y, -position.z)

    fun addRotationDeg(x: Float, y: Float) {
        rotation.add(
            Math.toRadians(x.toDouble()).toFloat(),
            Math.toRadians(y.toDouble()).toFloat()
        )
    }

    fun moveBackwards(inc: Float) {
        viewMatrix.positiveZ(direction).negate().mul(inc)
        position.sub(direction)
    }

    fun moveDown(inc: Float) {
        viewMatrix.positiveY(up).mul(inc)
        position.sub(up)
    }

    fun moveForward(inc: Float) {
        viewMatrix.positiveZ(direction).negate().mul(inc)
        position.add(direction)
    }

    fun moveLeft(inc: Float) {
        viewMatrix.positiveX(right).mul(inc)
        position.sub(right)
    }

    fun moveRight(inc: Float) {
        viewMatrix.positiveX(right).mul(inc)
        position.add(right)
    }

    fun moveUp(inc: Float) {
        viewMatrix.positiveY(up).mul(inc)
        position.add(up)
    }

    companion object {
        private const val MOUSE_SENSITIVITY = 0.1
    }
}