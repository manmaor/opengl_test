package com.example.spookycopengl.math.interfaces

import java.nio.FloatBuffer

interface Bufferable {
    fun toBuffer(buffer: FloatBuffer)
}