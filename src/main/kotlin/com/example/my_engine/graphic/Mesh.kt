package com.example.my_engine.graphic

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20
import java.nio.FloatBuffer
import java.nio.IntBuffer

class Mesh private constructor(
    val vao: VAO,
    val vertexCount: Int,
    private val vbos: List<VBO>
) {

    fun delete() {
        vbos.forEach { vbo ->
            vbo.delete()
        }
        vao.delete()
    }

    companion object {
        fun load(
            positions: FloatArray,
            textureCoords: FloatArray,
            indices: IntArray
        ): Mesh {
            val vao = VAO()
            val vbos = mutableListOf<VBO>()

            vao.bind()

            vbos.add(storeDataInAttributeList(0, 3, positions))
            vbos.add(storeDataInAttributeList(1, 2, textureCoords))
            vbos.add(bindIndicesBuffer(indices))

            vao.unbind()

            return Mesh(vao = vao, vertexCount = indices.size, vbos = vbos)
        }

        private fun bindIndicesBuffer(indices: IntArray): VBO {
            val vbo = VBO()
            val buffer = storeDataInIntBuffer(indices)
            vbo.bind(GL15.GL_ELEMENT_ARRAY_BUFFER)
            vbo.uploadElementArrayData(data = buffer)
            // Don't unbind the index buffer anywhere!
            // Each VAO has one special slot for an index buffer, and unbinding the index buffer will remove it from that slot
            return vbo
        }

        private fun storeDataInAttributeList(attributeNumber: Int, coordsSize: Int, data: FloatArray): VBO {
            val vbo = VBO()
            val buffer = storeDataInFloatBuffer(data)
            vbo.bind()
            vbo.uploadData(data = buffer)
            // enable = separate value from an array is used for each vertex.
            // disable = the current value of the attribute is used for all vertices
            GL20.glEnableVertexAttribArray(attributeNumber)
            GL20.glVertexAttribPointer(attributeNumber, coordsSize, GL11.GL_FLOAT, false, 0, 0)
            vbo.unbind()

            return vbo
        }

        private fun storeDataInIntBuffer(data: IntArray): IntBuffer {
            val buffer = BufferUtils.createIntBuffer(data.size)
            buffer.put(data)
            buffer.flip()
            return buffer
        }

        private fun storeDataInFloatBuffer(data: FloatArray): FloatBuffer {
            val buffer = BufferUtils.createFloatBuffer(data.size)
            buffer.put(data)
            buffer.flip() // tells openGl we finished
            return buffer
        }

    }

}