package com.example.thin_matrix.entities

import com.example.thin_matrix.models.TexturedModel
import org.joml.Vector3f

class Entity(
    val model: TexturedModel,
    val position: Vector3f,
    var rotX: Float,
    var rotY: Float,
    var rotZ: Float,
    val scale: Float
    ) {

    fun increasePosition(x: Float, y: Float, z: Float) {
        this.position.x += x
        this.position.y += y
        this.position.z += z
    }

    fun increaseRotation(x: Float, y: Float, z: Float) {
        rotX += x
        rotY += y
        rotZ += z
    }

}