package com.example.my_engine.scene

import com.example.my_engine.graphic.Model
import com.example.my_engine.graphic.Window
import com.example.my_engine.scene.lights.SceneLights

class Scene(
    window: Window
) {

    val modelEntitiesMap = mutableMapOf<Model, MutableList<Entity>>()
    val projection: Projection = Projection(window)
    val camera = Camera()
    var lights: SceneLights? = null

//    val textureManager = TextureManager()

    fun addEntity(entity: Entity) {
        val entitiesByModel = modelEntitiesMap.getOrPut(entity.model) { mutableListOf() }
        entitiesByModel.add(entity)
    }

    fun resize() {
        projection.update()
    }
}