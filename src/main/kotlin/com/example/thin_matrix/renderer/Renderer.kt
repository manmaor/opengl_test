package com.example.thin_matrix.renderer

import com.example.spookycopengl.graphic.Window
import com.example.thin_matrix.entities.Entity
import com.example.thin_matrix.shaders.StaticShader
import com.example.thin_matrix.toolbox.Maths
import org.joml.Matrix4f
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL13
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30


class Renderer(val window: Window, shader: StaticShader) {


    private val projectionMatrix: Matrix4f = createProjectionMatrix()

    init {
        shader.start()
        shader.loadProjectionMatrix(projectionMatrix)
        shader.stop()
    }

    fun prepare() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
    }

    fun render(entity: Entity, shader: StaticShader) {


        GL30.glBindVertexArray(entity.model.mesh.vao.id)
        GL20.glEnableVertexAttribArray(0)
        GL20.glEnableVertexAttribArray(1)

        GL13.glActiveTexture(GL13.GL_TEXTURE0)
        entity.model.texture.bind()

        val mat = Maths.createTransformationMatrix(entity.position, entity.rotX, entity.rotY, entity.rotZ, entity.scale)
        shader.loadTransformationMatrix(mat)

        GL11.glDrawElements(GL11.GL_TRIANGLES, entity.model.mesh.vertexCount, GL11.GL_UNSIGNED_INT, 0)


        GL20.glDisableVertexAttribArray(1)
        GL20.glDisableVertexAttribArray(0)
        GL30.glBindVertexArray(0)
    }

    private fun createProjectionMatrix(): Matrix4f {
        val (width, height) = window.size
        val aspectRatio = width.toFloat() / height.toFloat()
//        val yScale = (1f / tan(Math.toRadians( FOV.toDouble() / 2f))).toFloat() * aspectRatio
//        val xScale = yScale / aspectRatio
//        val frustumLength = FAR - NEAR
//
//        return Matrix4f().identity()
//            .m00(xScale)
//            .m11(yScale)
//            .m22(-((FAR + NEAR) / frustumLength))
//            .m23(-1f)
//            .m32(-((2* NEAR* FAR) / frustumLength))
//            .m33(0f)

        return Matrix4f().identity().perspective(FOV, aspectRatio, NEAR, FAR)

    }

    companion object {
        const val FOV = 70f
        const val NEAR = 0.1f
        const val FAR = 1000f
    }

    // GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.vertexCount) - old
    // GL11.glDrawElements(GL11.GL_TRIANGLES, model.vertexCount, GL11.GL_UNSIGNED_INT, 0) - new using indicies
}