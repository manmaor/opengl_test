package com.example.my_engine.graphic

import org.lwjgl.BufferUtils
import org.lwjgl.assimp.AIFace
import org.lwjgl.assimp.AIMesh
import org.lwjgl.assimp.AIVector3D
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
            val buffer = storeDataInIntBuffer(indices)
            return bindIndicesBuffer(buffer)
        }

        private fun bindIndicesBuffer(aiFaces: AIFace.Buffer): VBO {
            val buffer = storeDataInIntBuffer(aiFaces)
            return bindIndicesBuffer(buffer)
        }

        private fun bindIndicesBuffer(buffer: IntBuffer): VBO {
            val vbo = VBO()
            vbo.bind(GL15.GL_ELEMENT_ARRAY_BUFFER)
            vbo.uploadElementArrayData(data = buffer)
            // Don't unbind the index buffer anywhere!
            // Each VAO has one special slot for an index buffer, and unbinding the index buffer will remove it from that slot
            return vbo
        }

        private fun storeDataInAttributeList(attributeNumber: Int, coordsSize: Int, data: FloatArray): VBO {
            val buffer = storeDataInFloatBuffer(data)
            return storeDataInAttributeList(attributeNumber, coordsSize, buffer)
        }

        private fun storeDataInAttributeList(attributeNumber: Int, coordsSize: Int, data: AIVector3D.Buffer, flipY: Boolean = false): VBO {
            val buffer = storeDataInFloatBuffer(data, coordsSize, flipY)
            return storeDataInAttributeList(attributeNumber, coordsSize, buffer)
        }

        private fun storeDataInAttributeList(attributeNumber: Int, coordsSize: Int, buffer: FloatBuffer): VBO {
            val vbo = VBO()
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

        private fun storeDataInIntBuffer(aiFaces: AIFace.Buffer): IntBuffer {
            val buffer = BufferUtils.createIntBuffer(aiFaces.remaining() * 3)
            while (aiFaces.remaining() > 0) {
                val aiIndices = aiFaces.get().mIndices()
                while (aiIndices.remaining() > 0) {
                    buffer.put(aiIndices.get())
                }
            }
            buffer.flip()
            return buffer
        }

        private fun storeDataInFloatBuffer(aiBuffer: AIVector3D.Buffer, coordsSize: Int, flipY: Boolean = false) : FloatBuffer{
            val buffer = BufferUtils.createFloatBuffer(aiBuffer.remaining() * coordsSize)
            while (aiBuffer.remaining() > 0) {
                val vec = aiBuffer.get()
                when (coordsSize) {
                    2 -> buffer.put(vec.x()).put(if (flipY) (1-vec.y()) else vec.y())
                    3 -> buffer.put(vec.x()).put(vec.y()).put(vec.z())
                }

            }
            buffer.flip()
            return buffer
        }

        private fun storeDataInFloatBuffer(data: FloatArray): FloatBuffer {
            val buffer = BufferUtils.createFloatBuffer(data.size)
            buffer.put(data)
            buffer.flip() // tells openGl we finished
            return buffer
        }

        fun from(aiMesh: AIMesh): Mesh {
            val vao = VAO()
            val vbos = mutableListOf<VBO>()

            vao.bind()

            vbos.add(storeDataInAttributeList(0, 3, aiMesh.mVertices()))
            vbos.add(storeDataInAttributeList(1, 3, aiMesh.mNormals()!!))
            vbos.add(storeDataInAttributeList(2, 2, aiMesh.mTextureCoords(0)!!, true))
            val aiFaces = aiMesh.mFaces()
            val vertexCount = aiFaces.remaining() * 3
            vbos.add(bindIndicesBuffer(aiFaces))

            vao.unbind()

            return Mesh(vao = vao, vertexCount = vertexCount, vbos = vbos)
        }



    }

}