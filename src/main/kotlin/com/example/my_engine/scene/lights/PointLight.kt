package com.example.my_engine.scene.lights

import org.joml.Vector3f

// When modifying also modify fragment shader structs
data class PointLight(
    val attenuation: Attenuation = Attenuation(0f, 0f, 1f),
    val color: Vector3f,
    val position: Vector3f,
    val intensity: Float
)

// When modifying also modify fragment shader structs
data class Attenuation(
    val constant: Float,
    val exponent: Float,
    val linear: Float
)