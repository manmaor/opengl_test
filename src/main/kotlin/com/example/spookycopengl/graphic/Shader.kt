package com.example.spookycopengl.graphic

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20

class Shader private constructor(
    type: ShaderType,
    val id: Int = GL20.glCreateShader(type.openGLType)
) {

    fun source(source: String) {
        GL20.glShaderSource(id, source)
    }

    fun compile() {
        GL20.glCompileShader(id)
        checkStatus()
    }

    private fun checkStatus() {
        if (GL20.glGetShaderi(id, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            throw Error("Could not compile shader, log ${GL20.glGetShaderInfoLog(id, 500)}")
        }
    }

    fun delete() {
        GL20.glDeleteShader(id)
    }

    companion object {
        fun createShader(type: ShaderType, source: String): Shader
             = Shader(type).apply {
                source(source)
                compile()
            }

        fun loadShader(type: ShaderType, path: String): Shader {
            val source = Shader::class.java.classLoader.getResource(path)!!.readText()
            return createShader(type, source)
        }

    }
}

enum class ShaderType(val openGLType: Int) {
    Vertex(GL20.GL_VERTEX_SHADER),
    Fragment(GL20.GL_FRAGMENT_SHADER)
}