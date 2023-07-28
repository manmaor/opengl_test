package com.example.my_engine.graphic

import org.lwjgl.opengl.GL30

class VAO(
    val id: Int = GL30.glGenVertexArrays(),
) {

    var isDeleted: Boolean = false
        private set

    fun bind() {
        GL30.glBindVertexArray(id)
    }

    fun unbind() {
        GL30.glBindVertexArray(0)
    }

    fun delete() {
        if (!isDeleted) {
            GL30.glDeleteVertexArrays(id)
            isDeleted = true
        }
    }


}