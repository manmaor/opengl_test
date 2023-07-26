package com.example.spookycopengl.math.depricated

import com.example.spookycopengl.math.interfaces.Bufferable
import java.nio.FloatBuffer
import kotlin.math.sqrt

data class Vector3f(
    val x: Float = .0f,
    val y: Float = .0f,
    val z: Float = .0f
): Bufferable {

    val lengthSquared: Float
        get() = x*x + y+y + z*z

    val length: Float
        get() = sqrt(lengthSquared)


    fun normalize() = this / length


    operator fun Vector3f.plus(other: Vector3f) =
        Vector3f(this.x + other.x, this.y + other.y, this.z + other.z)

    operator fun Vector3f.unaryMinus() =
        Vector3f(-this.x, -this.y, -this.z)

    operator fun Vector3f.minus(other: Vector3f) =
        Vector3f(this.x - other.x, this.y - other.y, this.z - other.z)

    operator fun Vector3f.times(scalar: Float) =
        Vector3f(this.x * scalar, this.y * scalar, this.z * scalar)

    operator fun Vector3f.times(other: Vector3f) =
        Vector3f(this.x * other.x, this.y * other.y, this.z * other.z)

    operator fun Vector3f.div(scalar: Float) =
        Vector3f(this.x / scalar, this.y / scalar, this.z / scalar)


    infix fun dot(other: Vector3f) = (this * other).run { x + y + z }

    /**
     * Calculates the cross product of this vector with another vector.
     */
    infix fun cross(other: Vector3f) = Vector3f(
        this.y * other.z - this.z * other.y,
        this.z * other.x - this.x * other.z,
        this.x * other.y - this.y * other.x
    )

    /**
     * Calculates a linear interpolation between this vector with another
     * vector.
     */
    fun left(other: Vector3f, alpha: Float) {
        this * (1 - alpha) + other * alpha
    }

    override fun toBuffer(buffer: FloatBuffer) {
        buffer.put(x).put(y).put(z)
        buffer.flip()
    }
}