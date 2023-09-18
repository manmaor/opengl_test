package com.example.my_engine.scene.lights

import org.joml.Vector3f

// When modifying also modify fragment shader structs
data class AmbientLight(
    val intensity: Float,
    val color: Vector3f = Vector3f(1f, 1f, 1f)
)