package com.example.my_engine.math

import org.joml.Vector2f

operator fun Vector2f.minus(other: Vector2f): Vector2f = Vector2f().also {
    this.sub(other, it)
}