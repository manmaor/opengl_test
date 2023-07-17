package com.example.chapter2.shaders

object StaticShader: ShaderProgram(
    vertFile = "chapter2.vert",
    fragFile = "chapter2.frag"
) {

    override fun bindAttributes() {
        bindAttribute(0, "position")
    }

}