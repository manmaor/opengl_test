package com.example.my_engine.graphic

class Material(
    val texture: Texture,
    val meshes: List<Mesh>
) {

    fun delete() {
        meshes.forEach(Mesh::delete)
    }
}