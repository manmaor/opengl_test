package com.example.spookycopengl.math.depricated

import com.example.spookycopengl.math.interfaces.Bufferable
import java.nio.FloatBuffer


data class Matrix2f(
    val m00: Float = 1f, val m01: Float = 0f,
    val m10: Float = 0f, val m11: Float = 1f,
): Bufferable {

    constructor(col1: Vector2f, col2: Vector2f) : this(
        m00 = col1.x, m01 = col1.y,
        m10 = col2.x, m11 = col2.y,
    )


    operator fun Matrix2f.plus(other: Matrix2f) =
        Matrix2f(
            this.m00 + other.m00, this.m01 + other.m01,
            this.m10 + other.m10, this.m11 + other.m11
        )

    operator fun Matrix2f.unaryMinus() =
        Matrix2f(
            -this.m00, -this.m01,
            -this.m10, -this.m11
        )

    operator fun Matrix2f.minus(other: Matrix2f) =
        Matrix2f(
            this.m00 - other.m00, this.m01 - other.m01,
            this.m10 - other.m10, this.m11 - other.m11
        )

    operator fun Matrix2f.times(scalar: Float) =
        Matrix2f(
            this.m00 * scalar, this.m01 * scalar,
            this.m10 * scalar, this.m11 * scalar
        )

    operator fun Matrix2f.times(other: Vector2f) =
        Vector2f(
            this.m00 * other.x + this.m01 * other.y,
            this.m10 * other.x + this.m11 * other.y
        )

    operator fun Matrix2f.times(other: Matrix2f) =
        Matrix2f(
            this.m00 * other.m00 + this.m01 * other.m10, this.m00 * other.m01 + this.m01 * other.m11,
            this.m10 * other.m00 + this.m11 * other.m10, this.m10 * other.m01 + this.m11 * other.m11
        )

    operator fun Matrix2f.div(scalar: Float) =
        Matrix2f(
            this.m00 / scalar, this.m01 / scalar,
            this.m10 / scalar, this.m11 / scalar
        )

    fun transpose() = Matrix2f(
        this.m00, this.m10,
        this.m01, this.m11
    )

    override fun toBuffer(buffer: FloatBuffer) {
        buffer.put(m00).put(m10)
        buffer.put(m01).put(m11)
        buffer.flip()
    }
}