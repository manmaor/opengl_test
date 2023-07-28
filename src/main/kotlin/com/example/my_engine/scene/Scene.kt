package com.example.my_engine.scene

import com.example.my_engine.graphic.Model
import com.example.my_engine.graphic.TextureManager
import com.example.my_engine.graphic.Window

class Scene(
    window: Window
) {

    val modelEntitiesMap = mutableMapOf<Model, MutableList<Entity>>()
    val projection: Projection = Projection(window)
//    val textureManager = TextureManager()

    fun addEntity(entity: Entity) {
        val entitiesByModel = modelEntitiesMap.getOrPut(entity.model) { mutableListOf() }
        entitiesByModel.add(entity)
    }

    fun resize() {
        projection.update()
    }
}