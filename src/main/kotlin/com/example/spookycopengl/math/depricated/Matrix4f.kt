package com.example.spookycopengl.math.depricated

import com.example.spookycopengl.math.interfaces.Bufferable
import java.nio.FloatBuffer


data class Matrix4f(
    val m00: Float = 1f, val m01: Float = 0f, val m02: Float = 0f, val m03:Float = 0f,
    val m10: Float = 0f, val m11: Float = 1f, val m12: Float = 0f, val m13:Float = 0f,
    val m20: Float = 0f, val m21: Float = 0f, val m22: Float = 1f, val m23:Float = 0f,
    val m30: Float = 0f, val m31: Float = 0f, val m32: Float = 0f, val m33:Float = 1f
): Bufferable {


    constructor(col1: Vector4f, col2: Vector4f, col3: Vector4f, col4: Vector4f) : this(
        m00 = col1.x, m01 = col1.y, m02 = col1.z, m03 = col1.w,
        m10 = col2.x, m11 = col2.y, m12 = col2.z, m13 = col2.w,
        m20 = col3.x, m21 = col3.y, m22 = col3.z, m23 = col3.w,
        m30 = col4.x, m31 = col4.y, m32 = col4.z, m33 = col4.w
    )


    operator fun Matrix4f.plus(other: Matrix4f) =
        Matrix4f(
            this.m00 + other.m00, this.m01 + other.m01, this.m02 + other.m02,this.m03 + other.m03,
            this.m10 + other.m10, this.m11 + other.m11, this.m12 + other.m12,this.m13 + other.m13,
            this.m20 + other.m20, this.m21 + other.m21, this.m22 + other.m22, this.m23 + other.m23,
            this.m30 + other.m30, this.m31 + other.m31, this.m32 + other.m32, this.m33 + other.m33
        )

    operator fun Matrix4f.unaryMinus() =
        Matrix4f(
            -this.m00, -this.m01, -this.m02, -this.m03,
            -this.m10, -this.m11, -this.m12, -this.m13,
            -this.m20, -this.m21, -this.m22, -this.m23,
            -this.m30, -this.m31, -this.m32, -this.m33
        )

    operator fun Matrix4f.minus(other: Matrix4f) =
        Matrix4f(
            this.m00 - other.m00, this.m01 - other.m01, this.m02 - other.m02,this.m03 - other.m03,
            this.m10 - other.m10, this.m11 - other.m11, this.m12 - other.m12,this.m13 - other.m13,
            this.m20 - other.m20, this.m21 - other.m21, this.m22 - other.m22, this.m23 - other.m23,
            this.m30 - other.m30, this.m31 - other.m31, this.m32 - other.m32, this.m33 - other.m33
        )

    operator fun Matrix4f.times(scalar: Float) =
        Matrix4f(
            this.m00 * scalar, this.m01 * scalar, this.m02 * scalar,this.m03 * scalar,
            this.m10 * scalar, this.m11 * scalar, this.m12 * scalar,this.m13 * scalar,
            this.m20 * scalar, this.m21 * scalar, this.m22 * scalar, this.m23 * scalar,
            this.m30 * scalar, this.m31 * scalar, this.m32 * scalar, this.m33 * scalar
        )

    operator fun Matrix4f.times(other: Vector4f) =
        Vector4f(
            this.m00 * other.x + this.m01 * other.y + this.m02 * other.z + this.m03 * other.w,
            this.m10 * other.x + this.m11 * other.y + this.m12 * other.z + this.m13 * other.w,
            this.m20 * other.x + this.m21 * other.y + this.m22 * other.z + this.m23 * other.w,
            this.m30 * other.x + this.m31 * other.y + this.m32 * other.z + this.m33 * other.w
        )

    operator fun Matrix4f.times(other: Matrix4f) =
        Matrix4f(
            this.m00 * other.m00 + this.m01 * other.m10 + this.m02 * other.m20 + this.m03 * other.m30,
            this.m10 * other.m00 + this.m11 * other.m10 + this.m12 * other.m20 + this.m13 * other.m30,
            this.m20 * other.m00 + this.m21 * other.m10 + this.m22 * other.m20 + this.m23 * other.m30,
            this.m30 * other.m00 + this.m31 * other.m10 + this.m32 * other.m20 + this.m33 * other.m30,

            this.m00 * other.m01 + this.m01 * other.m11 + this.m02 * other.m21 + this.m03 * other.m31,
            this.m10 * other.m01 + this.m11 * other.m11 + this.m12 * other.m21 + this.m13 * other.m31,
            this.m20 * other.m01 + this.m21 * other.m11 + this.m22 * other.m21 + this.m23 * other.m31,
            this.m30 * other.m01 + this.m31 * other.m11 + this.m32 * other.m21 + this.m33 * other.m31,

            this.m00 * other.m02 + this.m01 * other.m12 + this.m02 * other.m22 + this.m03 * other.m32,
            this.m10 * other.m02 + this.m11 * other.m12 + this.m12 * other.m22 + this.m13 * other.m32,
            this.m20 * other.m02 + this.m21 * other.m12 + this.m22 * other.m22 + this.m23 * other.m32,
            this.m30 * other.m02 + this.m31 * other.m12 + this.m32 * other.m22 + this.m33 * other.m32,

            this.m00 * other.m03 + this.m01 * other.m13 + this.m02 * other.m23 + this.m03 * other.m33,
            this.m10 * other.m03 + this.m11 * other.m13 + this.m12 * other.m23 + this.m13 * other.m33,
            this.m20 * other.m03 + this.m21 * other.m13 + this.m22 * other.m23 + this.m23 * other.m33,
            this.m30 * other.m03 + this.m31 * other.m13 + this.m32 * other.m23 + this.m33 * other.m33
        )

    fun transpose() = Matrix4f(
        this.m00, this.m10, this.m20, this.m03,
        this.m01, this.m11, this.m21, this.m31,
        this.m02, this.m12, this.m22, this.m32,
        this.m03, this.m13, this.m23, this.m33
    )

    override fun toBuffer(buffer: FloatBuffer) {
        buffer.put(m00).put(m10).put(m20).put(m03)
        buffer.put(m01).put(m11).put(m21)
        buffer.put(m02).put(m12).put(m22)
        buffer.flip()
    }
}