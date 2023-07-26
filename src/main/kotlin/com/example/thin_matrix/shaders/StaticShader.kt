package com.example.thin_matrix.shaders

import org.joml.Matrix4f


class StaticShader(): ShaderProgram(
    vertFile = "chapter2.vert",
    fragFile = "chapter2.frag"
) {

    var location_transformationMatrix: Int = 0
    var location_projectionMatrix: Int = 0

    override fun bindAttributes() {
        program.bindAttribLocation(0, "position")
        program.bindAttribLocation(1, "textureCoords")
    }

    override fun getAllUniforms() {
        location_transformationMatrix = program.uniformLocationOf("transformationMatrix")
        location_projectionMatrix = program.uniformLocationOf("projectionMatrix")
    }

    fun loadTransformationMatrix(matrix: Matrix4f) {
        program.setUniform(location_transformationMatrix, matrix)
    }

    fun loadProjectionMatrix(matrix: Matrix4f) {
        program.setUniform(location_projectionMatrix, matrix)
    }

}