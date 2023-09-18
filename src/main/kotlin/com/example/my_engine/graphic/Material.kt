package com.example.my_engine.graphic

import org.joml.Vector4f
import org.lwjgl.assimp.AIColor4D
import org.lwjgl.assimp.AIMaterial
import org.lwjgl.assimp.Assimp
import org.lwjgl.assimp.Assimp.AI_MATKEY_SHININESS_STRENGTH
import org.lwjgl.assimp.Assimp.aiReturn_SUCCESS
import org.lwjgl.assimp.Assimp.aiTextureType_NONE


// When modifying also modify fragment shader structs
class Material(
    val texture: Texture,
    val ambient: Vector4f, // TODO: handle null
    val specular: Vector4f, // TODO: handle null
    val reflectance: Float,
    val meshes: List<Mesh>
) {

    fun delete() {
        meshes.forEach(Mesh::delete)
    }

    companion object {
        fun from(aiMaterial: AIMaterial, materialPath: String, meshes: List<Mesh>): Material {

            val ambient = getColor(aiMaterial, Assimp.AI_MATKEY_COLOR_AMBIENT)
            val specular = getColor(aiMaterial, Assimp.AI_MATKEY_COLOR_SPECULAR)

            val reflectance = getReflectance(aiMaterial)

//            val aiTexturePath = AIString.calloc()
//            Assimp.aiGetMaterialTexture(aiMaterial, Assimp.aiTextureType_DIFFUSE, 0, aiTexturePath, null as IntBuffer?, null, null, null, null, null)
//            val texturePath = aiTexturePath.dataString()
//            aiTexturePath.free()

            return Material(
                Texture.loadTexture(materialPath),
                ambient ?: Vector4f(0f),
                specular ?: Vector4f(0f),
                reflectance,
                meshes
            )
        }

        private fun getColor(aiMaterial: AIMaterial, pkey: String): Vector4f? {
            val color = AIColor4D.create()

            val result = Assimp.aiGetMaterialColor(aiMaterial, pkey, Assimp.aiTextureType_NONE, 0, color)
            if (result == Assimp.aiReturn_SUCCESS) {
                return Vector4f(color.r(), color.g(), color.b(), color.a())
            }

            color.free()

            return null
        }

        private fun getReflectance(aiMaterial: AIMaterial): Float {
            var reflectance = 0.0f
            val shininessFactor = floatArrayOf(0.0f)
            val pMax = intArrayOf(1)
            val result = Assimp.aiGetMaterialFloatArray(
                aiMaterial,
                AI_MATKEY_SHININESS_STRENGTH,
                aiTextureType_NONE,
                0,
                shininessFactor,
                pMax
            )
            if (result !== aiReturn_SUCCESS) {
                reflectance = shininessFactor[0]
            }
            return reflectance

        }
    }
}