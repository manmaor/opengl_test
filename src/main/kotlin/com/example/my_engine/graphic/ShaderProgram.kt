package com.example.my_engine.graphic

import com.example.my_engine.math.toBuffer
import org.joml.*
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
import org.lwjgl.system.MemoryStack
import java.nio.FloatBuffer

class ShaderProgram(
    val id: Int = GL20.glCreateProgram()
) {

    fun attachShader(shader: Shader) {
        GL20.glAttachShader(id, shader.id)
    }

    /**
     * Binds the fragment out color variable
     */
    fun bindFragmentDataLocation(colorNumber: Int, name: String) {
        GL30.glBindFragDataLocation(id, colorNumber, name)
    }

    fun link() {
        GL20.glLinkProgram(id)

        checkStatus()
    }

    fun locationAttributeOf(name: String): Int {
        return GL20.glGetAttribLocation(id, name)
    }

    /**
     * this is not needed if using layout (location) in the shader
     */
    fun bindAttribLocation(index: Int, name: String) {
        GL20.glBindAttribLocation(id, index, name)
    }

    fun enableVertexAttribute(location: Int) {
        GL20.glEnableVertexAttribArray(location)
    }

    fun pointVertexAttribute(location: Int, size: Int, stride: Int, offset: Long) {
        GL20.glVertexAttribPointer(location, size, GL20.GL_FLOAT, false, stride, offset)
    }

    fun uniformLocationOf(name: String): Uniform {
        return Uniform(GL20.glGetUniformLocation(id, name))
    }

    fun <T: Any> setUniformData(uniform: Uniform, value: T) {
        when (value::class) {
            Int::class -> {
                GL20.glUniform1i(uniform.location, value as Int)
            }
            Float::class -> {
                GL20.glUniform1f(uniform.location, value as Float)
            }
            Vector2f::class -> {
                MemoryStack.stackPush().use {
                    val buffer: FloatBuffer = it.mallocFloat(2)
                    (value as Vector2f).toBuffer(buffer)
                    GL20.glUniform2fv(uniform.location, buffer)
                }
            }
            Vector3f::class -> {
                MemoryStack.stackPush().use { stack ->
                    val buffer = stack.mallocFloat(3)
                    (value as Vector3f).toBuffer(buffer)
                    GL20.glUniform3fv(uniform.location, buffer)
                }
            }
            Vector4f::class -> {
                MemoryStack.stackPush().use { stack ->
                    val buffer = stack.mallocFloat(4)
                    (value as Vector4f).toBuffer(buffer)
                    GL20.glUniform3fv(uniform.location, buffer)
                }
            }
//            Matrix2f::class -> {
//                MemoryStack.stackPush().use { stack ->
//                    val buffer = stack.mallocFloat(2*2)
//                    (value as Matrix2f).toBuffer(buffer)
//                    GL20.glUniformMatrix2fv(location, false, buffer)
//                }
//            }
            Matrix3f::class -> {
                MemoryStack.stackPush().use { stack ->
                    val buffer = stack.mallocFloat(3*3)
                    (value as Matrix3f).toBuffer(buffer)
                    GL20.glUniformMatrix3fv(uniform.location, false, buffer)
                }
            }
            Matrix4f::class -> {
                MemoryStack.stackPush().use { stack ->
                    val buffer = stack.mallocFloat(4*4)
                    (value as Matrix4f).toBuffer(buffer)
                    GL20.glUniformMatrix4fv(uniform.location, false, buffer)
                }
            }
        }
    }

    fun bind() {
        GL20.glUseProgram(id)
    }

    fun unbind() {
        GL20.glUseProgram(0)
    }

    fun detachShader(shader: Shader) {
        GL20.glDetachShader(id, shader.id)
    }

    fun checkStatus() {
        val status = GL20.glGetProgrami(id, GL20.GL_LINK_STATUS)
        if (status != GL11.GL_TRUE) {
            // throw Error("Could not compile shader filename ${file}, log ${GL20.glGetShaderInfoLog(shaderID, 500)}")
            throw Error("ShaderProgram, checkFailed reason: ${GL20.glGetProgramInfoLog(id)}")
        }
    }

    fun delete() {
        GL20.glDeleteShader(id)
    }

}