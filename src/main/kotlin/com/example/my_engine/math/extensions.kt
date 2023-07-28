package com.example.my_engine.math

import org.joml.*
import java.nio.FloatBuffer

fun Vector2f.toBuffer(buffer: FloatBuffer) {
    buffer.put(x).put(y)
    buffer.flip()
}

fun Vector3f.toBuffer(buffer: FloatBuffer) {
    buffer.put(x).put(y).put(z)
    buffer.flip()
}

fun Vector4f.toBuffer(buffer: FloatBuffer) {
    buffer.put(x).put(y).put(z).put(w)
    buffer.flip()
}

fun Matrix3f.toBuffer(buffer: FloatBuffer) {
    buffer.put(m00).put(m01).put(m02)
    buffer.put(m10).put(m11).put(m12)
    buffer.put(m20).put(m21).put(m22)
    buffer.flip()
}

fun Matrix4f.toBuffer(buffer: FloatBuffer) {
    buffer.put(m00()).put(m01()).put(m02()).put(m03())
    buffer.put(m10()).put(m11()).put(m12()).put(m13())
    buffer.put(m20()).put(m21()).put(m22()).put(m23())
    buffer.put(m30()).put(m31()).put(m32()).put(m33())
    buffer.flip()
}