package com.example.thin_matrix.shaders

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20

abstract class ShaderProgram(
    vertFile: String,
    fragFile: String
) {

    private val programId: Int
    private val vertShaderId: Int
    private val fragShaderId: Int

    init {
        vertShaderId = loaderShader(vertFile, GL20.GL_VERTEX_SHADER)
        fragShaderId = loaderShader(fragFile, GL20.GL_FRAGMENT_SHADER)
        programId = GL20.glCreateProgram()
        GL20.glAttachShader(programId, vertShaderId)
        GL20.glAttachShader(programId, fragShaderId)
        GL20.glLinkProgram(programId)
        GL20.glValidateProgram(programId)
    }

    fun start() {
        GL20.glUseProgram(programId)
    }

    fun stop() {
        GL20.glUseProgram(0)
    }

    fun print() {
        println(GL20.glGetShaderInfoLog(programId))
    }

    fun clean() {
        stop()
        GL20.glDetachShader(programId, vertShaderId)
        GL20.glDetachShader(programId, fragShaderId)
        GL20.glDeleteShader(vertShaderId)
        GL20.glDeleteShader(fragShaderId)
        GL20.glDeleteProgram(programId)
    }

    abstract fun bindAttributes()

    protected fun bindAttribute(attribute: Int, variableName: String) {
        GL20.glBindAttribLocation(programId, attribute, variableName)
    }

    companion object {
        fun loaderShader(file: String, type: Int): Int {

            val shaderSource = ShaderProgram::class.java.classLoader.getResource(file)?.readText()!!
            val shaderID = GL20.glCreateShader(type)
            GL20.glShaderSource(shaderID, shaderSource)
            GL20.glCompileShader(shaderID)

            if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
                throw Error("Could not compile shader filename ${file}, log ${GL20.glGetShaderInfoLog(shaderID, 500)}")
            }

            return shaderID
        }
    }

}