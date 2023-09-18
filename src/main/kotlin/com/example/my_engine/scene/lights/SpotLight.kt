package com.example.my_engine.scene.lights

import org.joml.Math
import org.joml.Vector3f
import kotlin.math.cos

// When modifying also modify fragment shader structs
data class SpotLight(
    val pointLight: PointLight,
    val coneDirection: Vector3f,
    val cutOffAngle: Float
){
    val cutOff: Float = cos(Math.toRadians(cutOffAngle.toDouble())).toFloat()
}