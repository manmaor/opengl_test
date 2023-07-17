package com.example.spookycopengl.graphic

import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL30
import java.nio.FloatBuffer
import java.nio.IntBuffer

class VBO(
    val id: Int = GL30.glGenBuffers()
) {

    var isDeleted: Boolean = false
        private set

    fun bind(target: Int = GL15.GL_ARRAY_BUFFER) {
        GL30.glBindBuffer(target, id)
    }

    fun unbind(target: Int = GL15.GL_ARRAY_BUFFER) {
        GL30.glBindBuffer(target, 0)
    }

    fun delete() {
        if (!isDeleted) {
            GL30.glDeleteBuffers(id)
            isDeleted = true
        }
    }

    fun uploadData(
        target: Int = GL15.GL_ARRAY_BUFFER,
        data: FloatBuffer,
        usage: Int = GL15.GL_STATIC_DRAW) {
        GL30.glBufferData(target, data, usage)
    }

    /**
     * Upload null data to this VBO with specified target, size and usage.
     *
     * @param target Target to upload
     * @param size   Size in bytes of the VBO data store
     * @param usage  Usage of the data
     */
    fun uploadData(
        target: Int = GL15.GL_ARRAY_BUFFER,
        size: Long,
        usage: Int = GL15.GL_STATIC_DRAW) {
        GL30.glBufferData(target, size, usage)
    }

    fun uploadSubData(
        target: Int = GL15.GL_ARRAY_BUFFER,
        offset: Long,
        data: FloatBuffer
    ) {
        GL30.glBufferSubData(target, offset, data)
    }

    fun uploadElementArrayData(
        target: Int = GL15.GL_ELEMENT_ARRAY_BUFFER,
        data: IntBuffer,
        usage: Int = GL15.GL_STATIC_DRAW) {
        GL30.glBufferData(target, data, usage)
    }


}