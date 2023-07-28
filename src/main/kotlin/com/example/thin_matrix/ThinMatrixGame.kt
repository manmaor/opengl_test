package com.example.thin_matrix

import com.example.my_engine.Engine
import com.example.my_engine.graphic.*
import com.example.my_engine.scene.Entity
import com.example.my_engine.scene.EntityId

class ThinMatrixGame: Engine() {

    private val meshes = mutableListOf<Mesh>()
    private val textures = mutableListOf<Texture>()

    init {
        val vertices = arrayListOf(
            -.5f, .5f, 0f, // v0
            -.5f, -.5f, 0f, // v1
            .5f, -.5f, 0f, // v2
            .5f, .5f, 0f   // v3
        ).toFloatArray()

        val indices = arrayListOf(
            0,1,3,
            3,1,2
        ).toIntArray()

        val textureCoords = arrayListOf(
            0f,0f, // v0
            0f,1f, // v1
            1f,1f, // v2
            1f,0f // v3
        ).toFloatArray()

        val mesh = Mesh.load(vertices, textureCoords, indices)
        meshes.add(mesh)
        val texture = Texture.loadTexture("hello_world.png")
        textures.add(texture)
        val material = Material(texture, listOf(mesh))
        val model = Model(ModelId("cube-model"), listOf(material))
        val entity = Entity(id = EntityId("cube_entity"), model = model)

        scene.addEntity(entity)
    }

    override fun input(delta: Long) {
    }

    override fun update(delta: Long) {
        val entity = scene.modelEntitiesMap.values.first().first()
        entity.increasePosition(0f, 0f, -0.02f)
        entity.increaseRotation(0f, 1f, 0f)
    }

    override fun delete() {
        meshes.forEach { it.delete() }
        textures.forEach { it.delete() }
    }

}