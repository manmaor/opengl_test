package com.example.my_engine.graphic.render

import com.example.my_engine.graphic.*
import com.example.my_engine.scene.Scene
import com.example.my_engine.scene.lights.AmbientLight
import org.joml.Vector3f
import org.joml.Vector4f
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL13

class SceneRenderer {

    private val program: ShaderProgram = ShaderProgram()

    private val uniformProjectionMatrix: Uniform
    private val uniformViewMatrix: Uniform
    private val uniformModelMatrix: Uniform

    private val uniformMaterialAmbient: Uniform
//    private val uniformMaterialDiffuse: Uniform
    private val uniformMaterialSpecular: Uniform
    private val uniformMaterialReflectance: Uniform
    private val uniformAmbientFactor: Uniform
    private val uniformAmbientColor: Uniform

    private val uniformDirLightColor: Uniform
    private val uniformDirLightDirection: Uniform
    private val uniformDirLightIntencity: Uniform


    init {
        setUpShaderProgram()

        uniformProjectionMatrix = program.uniformLocationOf("projectionMatrix")
        uniformViewMatrix = program.uniformLocationOf("viewMatrix")
        uniformModelMatrix = program.uniformLocationOf("modelMatrix")

        uniformMaterialAmbient = program.uniformLocationOf("material.ambient")
//        uniformMaterialDiffuse = program.uniformLocationOf("material.diffuse")
        uniformMaterialSpecular = program.uniformLocationOf("material.specular")
        uniformMaterialReflectance = program.uniformLocationOf("material.reflectance")
        uniformAmbientFactor = program.uniformLocationOf("ambientLight.factor")
        uniformAmbientColor = program.uniformLocationOf("ambientLight.color")

        // for pointLights
        // for SpotLights

        uniformDirLightColor = program.uniformLocationOf("dirLight.color")
        uniformDirLightDirection = program.uniformLocationOf("dirLight.direction")
        uniformDirLightIntencity = program.uniformLocationOf("dirLight.intensity")

    }

    private fun setUpShaderProgram() {
        val vertShader = Shader.loadShader(ShaderType.Vertex, "chapter2.vert")
        val fragShader = Shader.loadShader(ShaderType.Fragment, "chapter2.frag")
        program.attachShader(vertShader)
        program.attachShader(fragShader)
        program.link()

        // after we linked the program we don't need the shaders
        program.detachShader(vertShader)
        program.detachShader(fragShader)
        vertShader.delete()
        fragShader.delete()
    }

    fun delete() {
        program.unbind()
        program.delete()
    }

    fun render(scene: Scene) {
        program.bind()

        program.setUniformData(uniformProjectionMatrix, scene.projection.projectionMatrix)
        program.setUniformData(uniformViewMatrix, scene.camera.viewMatrix)

        updateLights(scene)

        scene.modelEntitiesMap.forEach { (model, entities) ->
            model.materials.forEach { material ->
                program.setUniformData(uniformMaterialAmbient, material.ambient)
//                program.setUniformData(uniformMaterialDiffuse, material.diffuse)
                program.setUniformData(uniformMaterialSpecular, material.specular)
                program.setUniformData(uniformMaterialReflectance, material.reflectance)

                val texture = material.texture // scene.textureManager.textureMap[material.texturePath]!!
                GL13.glActiveTexture(GL13.GL_TEXTURE0)
                texture.bind()

                material.meshes.forEach { mesh ->
                    mesh.vao.bind()
                    entities.forEach { entity ->
                        program.setUniformData(uniformModelMatrix, entity.modelMatrix)
                        GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.vertexCount, GL11.GL_UNSIGNED_INT, 0)
                    }
                    mesh.vao.unbind() // can be optimized by unbinding only once at the end
                }
            }
        }

        program.unbind()
    }

    private fun updateLights(scene: Scene) {
        val viewMatrix = scene.camera.viewMatrix

        // TODO: check if null
        scene.lights ?.let { sceneLights ->
            val ambientLight = sceneLights.ambientLight
            program.setUniformData(uniformAmbientFactor, ambientLight.intensity)
            program.setUniformData(uniformAmbientColor, ambientLight.color)

            val dirLight = sceneLights.dirLight

            val auxDir = Vector4f(dirLight.direction, 1f)
            auxDir.mul(viewMatrix)
            val dir = Vector3f(auxDir.x, auxDir.y, auxDir.z)
            program.setUniformData(uniformDirLightColor, dirLight.color)
            program.setUniformData(uniformDirLightDirection, dir)
            program.setUniformData(uniformDirLightIntencity, dirLight.intensity)
        }


        // update point lights
        // update spot lights

    }

}