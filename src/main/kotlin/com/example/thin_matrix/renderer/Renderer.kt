package com.example.thin_matrix.renderer

import com.example.spookycopengl.graphic.Window
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30


class Renderer {

    fun prepare() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
    }

    fun render(model: RawModel) {

        GL30.glBindVertexArray(model.vaoId)
        GL20.glEnableVertexAttribArray(0)

//        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.vertexCount)
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.vertexCount, GL11.GL_UNSIGNED_INT, 0)

        GL20.glDisableVertexAttribArray(0)
        GL30.glBindVertexArray(0)
    }
}