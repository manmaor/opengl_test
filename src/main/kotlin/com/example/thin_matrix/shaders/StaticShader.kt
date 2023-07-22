package com.example.thin_matrix.shaders

class StaticShader(): ShaderProgram(
    vertFile = "chapter2.vert",
    fragFile = "chapter2.frag"
) {

    override fun bindAttributes() {
        bindAttribute(0, "position")
    }

}