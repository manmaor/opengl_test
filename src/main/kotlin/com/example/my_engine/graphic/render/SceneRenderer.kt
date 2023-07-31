package com.example.my_engine.graphic.render

import com.example.my_engine.graphic.*
import com.example.my_engine.scene.Scene
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL13

class SceneRenderer {

    private val program: ShaderProgram = ShaderProgram()

    private val uniformProjectionMatrix: Uniform
    private val uniformViewMatrix: Uniform
    private val uniformModelMatrix: Uniform

    init {
        setUpShaderProgram()

        uniformProjectionMatrix = program.uniformLocationOf("projectionMatrix")
        uniformViewMatrix = program.uniformLocationOf("viewMatrix")
        uniformModelMatrix = program.uniformLocationOf("modelMatrix")
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

        scene.modelEntitiesMap.forEach { (model, entities) ->
            model.materials.forEach { material ->
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

}