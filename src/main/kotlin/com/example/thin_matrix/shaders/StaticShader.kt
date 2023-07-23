package com.example.thin_matrix.shaders


class StaticShader(): ShaderProgram(
    vertFile = "chapter2.vert",
    fragFile = "chapter2.frag"
) {

    override fun bindAttributes() {
        program.bindAttribLocation(0, "position")
        program.bindAttribLocation(1, "textureCoords")
    }

}