package com.example.my_engine.graphic

@JvmInline
value class ModelId(val value: String)

class Model(
    val id: ModelId,
    val materials: List<Material>
) {
    // requires for entities map in scene
    override fun equals(other: Any?): Boolean {
        return other?.let { it as? Model }?.id == this.id
    }
}