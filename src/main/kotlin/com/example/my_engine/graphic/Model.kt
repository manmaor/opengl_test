package com.example.my_engine.graphic

import org.lwjgl.assimp.AIMaterial
import org.lwjgl.assimp.AIMesh
import org.lwjgl.assimp.Assimp

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

    companion object {
        fun loadModel(modelId: ModelId, modelPath: String, materialPath: String): Model {
            return loadModel(
                modelId,
                modelPath,
                materialPath,
                Assimp.aiProcess_JoinIdenticalVertices

            )
            /*
            aiProcess_GenSmoothNormals - ?
            aiProcess_JoinIdenticalVertices - as sound
            aiProcess_Triangulate - in case of complex geometry
            aiProcess_FixInfacingNormals - as sound
            aiProcess_CalcTangentSpace - calculate the normals in the tanget space????
            aiProcess_LimitBoneWeights - limit the number of weights that affect a single vertex
            aiProcess_PreTransformVertices - corrects the axis in models
            */
        }

        fun loadModel(modelId: ModelId, modelPath: String, materialPath: String, flags: Int): Model {
            val modelResource = Model::class.java.classLoader.getResource(modelPath)

            val aiScene = Assimp.aiImportFile(modelResource.path, flags)
                ?: throw Exception("Error loading model [modelPath: $modelPath]")


            val materialIndexToMeshMap = (0 until aiScene.mNumMeshes())
                .map { AIMesh.create(aiScene.mMeshes()!!.get(it)) }
                .groupBy({ aiMesh ->
                    val materialIndex = aiMesh.mMaterialIndex()
                    materialIndex
                }, { aiMesh ->
                    Mesh.from(aiMesh)
                })

            // if (materialIndex >= 0 && materialIndex < materials.size)

            val materials = (0 until aiScene.mNumMaterials()).mapNotNull {
                val aiMaterial = AIMaterial.create(aiScene.mMaterials()!!.get(it))
                materialIndexToMeshMap[it]?.let { meshes ->
                    Material.from(aiMaterial, materialPath, meshes)
                }.also { material ->
                    material ?: println("No meshes found for material number $it")
                }

            }

            return Model(modelId, materials)

        }
    }
}