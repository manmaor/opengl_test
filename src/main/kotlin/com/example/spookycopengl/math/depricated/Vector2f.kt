package com.example.spookycopengl.math.depricated

import com.example.spookycopengl.math.interfaces.Bufferable
import java.nio.FloatBuffer
import kotlin.math.sqrt

data class Vector2f(
    val x: Float = .0f,
    val y: Float = .0f
): Bufferable {

    val lengthSquared: Float
        get() = x*x + y+y

    val length: Float
        get() = sqrt(lengthSquared)


    fun normalize() = this / length


    operator fun Vector2f.plus(other: Vector2f) =
        Vector2f(this.x + other.x, this.y + other.y)

    operator fun Vector2f.unaryMinus() =
        Vector2f(-this.x, -this.y)

    operator fun Vector2f.minus(other: Vector2f) =
        Vector2f(this.x - other.x, this.y - other.y)

    operator fun Vector2f.times(scalar: Float) =
        Vector2f(this.x * scalar, this.y * scalar)

    operator fun Vector2f.times(other: Vector2f) =
        Vector2f(this.x * other.x, this.y * other.y)

    operator fun Vector2f.div(scalar: Float) =
        Vector2f(this.x / scalar, this.y / scalar)


    infix fun dot(other: Vector2f) = (this * other).run { x + y }


    /**
     * Calculates a linear interpolation between this vector with another
     * vector.
     */
    fun left(other: Vector2f, alpha: Float) {
        this * (1 - alpha) + other * alpha
    }

    override fun toBuffer(buffer: FloatBuffer) {
        buffer.put(x).put(y)
        buffer.flip()
    }
}