package com.example.thin_matrix.renderer

import com.example.spookycopengl.graphic.Texture
import com.example.spookycopengl.graphic.VAO
import com.example.spookycopengl.graphic.VBO
import com.example.thin_matrix.models.RawModel
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20
import java.nio.FloatBuffer
import java.nio.IntBuffer


object Loader {

    private val vaoCache = mutableListOf<VAO>()
    private val vboCache = mutableListOf<VBO>()
    private val texCache = mutableListOf<Texture>()

    fun loadToVAO(positions: FloatArray, textureCoords: FloatArray, indices: IntArray): RawModel {
        val vao = VAO()
        vaoCache.add(vao)
        vao.bind()
        storeDataInAttributeList(0, 3, positions)
        storeDataInAttributeList(1, 2, textureCoords)
        bindIndicesBuffer(indices)
        vao.unbind()
        return RawModel(vao.id, indices.size)
    }

    fun loadTexture(path: String): Texture {
        val texture = Texture.loadTexture(path)
        texCache.add(texture)
        return texture
    }

    fun clean() {
        vaoCache.forEach { it.delete() }
        vboCache.forEach { it.delete() }
        texCache.forEach { it.delete() }
    }

    private fun bindIndicesBuffer(indices: IntArray) {
        val vbo = VBO()
        vboCache.add(vbo)
        vbo.bind(GL15.GL_ELEMENT_ARRAY_BUFFER)
        val buffer = storeDataInIntBuffer(indices)
        vbo.uploadElementArrayData(data = buffer)
        // Don't unbind the index buffer anywhere!
        // Each VAO has one special slot for an index buffer, and unbinding the index buffer will remove it from that slot
    }


    private fun storeDataInAttributeList(attributeNumber: Int, coordinateSize: Int, data: FloatArray) {
        val vbo = VBO()
        vboCache.add(vbo)
        vbo.bind()

        val buffer = storeDataInFloatBuffer(data)
        vbo.uploadData(data = buffer)
        // GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW) //  GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW) // why not this?
        // pointer  - start location of data
        GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0)
        vbo.unbind()
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