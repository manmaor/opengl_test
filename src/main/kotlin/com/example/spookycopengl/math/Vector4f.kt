package com.example.spookycopengl.math

import com.example.spookycopengl.math.interfaces.Bufferable
import java.nio.FloatBuffer
import kotlin.math.sqrt

data class Vector4f(
    val x: Float = .0f,
    val y: Float = .0f,
    val z: Float = .0f,
    val w: Float = .0f
): Bufferable {

    val lengthSquared: Float
        get() = x*x + y+y + z*z + w*w

    val length: Float
        get() = sqrt(lengthSquared)


    fun normalize() = this / length


    operator fun Vector4f.plus(other: Vector4f) =
        Vector4f(this.x + other.x, this.y + other.y, this.z + other.z, this.w + other.w)

    operator fun Vector4f.unaryMinus() =
        Vector4f(-this.x, -this.y, -this.z, -this.w)

    operator fun Vector4f.minus(other: Vector4f) =
        Vector4f(this.x - other.x, this.y - other.y, this.z - other.z, this.w - other.w)

    operator fun Vector4f.times(scalar: Float) =
        Vector4f(this.x * scalar, this.y * scalar, this.z * scalar, this.w * scalar)

    operator fun Vector4f.times(other: Vector4f) =
        Vector4f(this.x * other.x, this.y * other.y, this.z * other.z, this.w * other.w)

    operator fun Vector4f.div(scalar: Float) =
        Vector4f(this.x / scalar, this.y / scalar, this.z / scalar, this.w / scalar)


    infix fun dot(other: Vector4f) = (this * other).run { x + y + z + w }

    /**
     * Calculates a linear interpolation between this vector with another
     * vector.
     */
    fun left(other: Vector4f, alpha: Float) {
        this * (1 - alpha) + other * alpha
    }

    override fun toBuffer(buffer: FloatBuffer) {
        buffer.put(x).put(y).put(z).put(w)
        buffer.flip()
    }
}