package com.example.my_engine.graphic

import org.lwjgl.assimp.AIColor4D
import org.lwjgl.assimp.AIMaterial
import org.lwjgl.assimp.AIString
import org.lwjgl.assimp.Assimp
import org.lwjgl.system.MemoryStack
import java.nio.IntBuffer

class Material(
    val texture: Texture,
    val meshes: List<Mesh>
) {

    fun delete() {
        meshes.forEach(Mesh::delete)
    }

    companion object {
        fun from(aiMaterial: AIMaterial, materialPath: String, meshes: List<Mesh>): Material {

//            val color = AIColor4D.create()
//            val result = Assimp.aiGetMaterialColor(aiMaterial, Assimp.AI_MATKEY_COLOR_DIFFUSE, Assimp.aiTextureType_NONE, 0, color)
//            if (result == Assimp.aiReturn_SUCCESS)

//            MemoryStack.stackPush().use {
//
//            }

//            val aiTexturePath = AIString.calloc()
//            Assimp.aiGetMaterialTexture(aiMaterial, Assimp.aiTextureType_DIFFUSE, 0, aiTexturePath, null as IntBuffer?, null, null, null, null, null)
//            val texturePath = aiTexturePath.dataString()
//            aiTexturePath.free()

            return Material(Texture.loadTexture(materialPath), meshes)
        }
    }
}