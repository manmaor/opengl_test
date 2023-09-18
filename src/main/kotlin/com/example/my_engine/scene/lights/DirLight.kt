package com.example.my_engine.scene.lights

import org.joml.Vector3f

// When modifying also modify fragment shader structs
data class DirLight(
    val color: Vector3f,
    val direction: Vector3f,
    val intensity: Float
)