package com.example.thin_matrix.shaders

import com.example.spookycopengl.graphic.Shader
import com.example.spookycopengl.graphic.ShaderProgram
import com.example.spookycopengl.graphic.ShaderType


abstract class ShaderProgram(
    vertFile: String,
    fragFile: String
) {

    val program: ShaderProgram
    private val vertShader: Shader
    private val fragShader: Shader

    init {
        vertShader = Shader.loadShader(ShaderType.Vertex, vertFile)
        fragShader = Shader.loadShader(ShaderType.Fragment, fragFile)
        program = ShaderProgram()
        program.attachShader(vertShader)
        program.attachShader(fragShader)
        bindAttributes()
        program.link()
    }

    fun start() {
        program.use()
    }

    fun stop() {
        program.disuse()
    }


    fun clean() {
        stop()
        program.detachShader(vertShader)
        program.detachShader(fragShader)
        vertShader.delete()
        fragShader.delete()
        program.delete()
    }

    abstract fun bindAttributes()

}