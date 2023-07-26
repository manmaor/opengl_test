package com.example.spookycopengl.math.depricated

import com.example.spookycopengl.math.interfaces.Bufferable
import java.nio.FloatBuffer


data class Matrix3f(
    val m00: Float = 1f, val m01: Float = 0f, val m02: Float = 0f,
    val m10: Float = 0f, val m11: Float = 1f, val m12: Float = 0f,
    val m20: Float = 0f, val m21: Float = 0f, val m22: Float = 1f,
): Bufferable {

    constructor(col1: Vector3f, col2: Vector3f, col3: Vector3f) : this(
        m00 = col1.x, m01 = col1.y, m02 = col1.z,
        m10 = col2.x, m11 = col2.y, m12 = col2.z,
        m20 = col3.x, m21 = col3.y, m22 = col3.z,
    )


    operator fun Matrix3f.plus(other: Matrix3f) =
        Matrix3f(
            this.m00 + other.m00, this.m01 + other.m01, this.m02 + other.m02,
            this.m10 + other.m10, this.m11 + other.m11, this.m12 + other.m12,
            this.m20 + other.m20, this.m21 + other.m21, this.m22 + other.m22
        )

    operator fun Matrix3f.unaryMinus() =
        Matrix3f(
            -this.m00, -this.m01, -this.m02,
            -this.m10, -this.m11, -this.m12,
            -this.m20, -this.m21, -this.m22
        )

    operator fun Matrix3f.minus(other: Matrix3f) =
        Matrix3f(
            this.m00 - other.m00, this.m01 - other.m01, this.m02 - other.m02,
            this.m10 - other.m10, this.m11 - other.m11, this.m12 - other.m12,
            this.m20 - other.m20, this.m21 - other.m21, this.m22 - other.m22
        )

    operator fun Matrix3f.times(scalar: Float) =
        Matrix3f(
            this.m00 * scalar, this.m01 * scalar, this.m02 * scalar,
            this.m10 * scalar, this.m11 * scalar, this.m12 * scalar,
            this.m20 * scalar, this.m21 * scalar, this.m22 * scalar
        )

    operator fun Matrix3f.times(other: Vector3f) =
        Vector3f(
            this.m00 * other.x + this.m01 * other.y + this.m02 * other.z,
            this.m10 * other.x + this.m11 * other.y + this.m12 * other.z,
            this.m20 * other.x + this.m21 * other.y + this.m22 * other.z
        )

    operator fun Matrix3f.times(other: Matrix3f) =
        Matrix3f(
            this.m00 * other.m00 + this.m01 * other.m10 + this.m02 * other.m20,
            this.m10 * other.m00 + this.m11 * other.m10 + this.m12 * other.m20,
            this.m20 * other.m00 + this.m21 * other.m10 + this.m22 * other.m20,
            this.m00 * other.m01 + this.m01 * other.m11 + this.m02 * other.m21,
            this.m10 * other.m01 + this.m11 * other.m11 + this.m12 * other.m21,
            this.m20 * other.m01 + this.m21 * other.m11 + this.m22 * other.m21,
            this.m00 * other.m02 + this.m01 * other.m12 + this.m02 * other.m22,
            this.m10 * other.m02 + this.m11 * other.m12 + this.m12 * other.m22,
            this.m20 * other.m02 + this.m21 * other.m12 + this.m22 * other.m22
        )

    fun transpose() = Matrix3f(
        this.m00, this.m10, this.m20,
        this.m01, this.m11, this.m21,
        this.m02, this.m12, this.m22
    )

    override fun toBuffer(buffer: FloatBuffer) {
        buffer.put(m00).put(m10).put(m20)
        buffer.put(m01).put(m11).put(m21)
        buffer.put(m02).put(m12).put(m22)
        buffer.flip()
    }
}