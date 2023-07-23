package com.example.thin_matrix.renderer

import com.example.thin_matrix.models.RawModel
import com.example.thin_matrix.models.TexturedModel
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL13
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30


class Renderer {

    fun prepare() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
    }

    fun render(texturedModel: TexturedModel) {

        GL30.glBindVertexArray(texturedModel.model.vaoId)
        GL20.glEnableVertexAttribArray(0)
        GL20.glEnableVertexAttribArray(1)

        GL13.glActiveTexture(GL13.GL_TEXTURE0)
        texturedModel.texture.bind()

        GL11.glDrawElements(GL11.GL_TRIANGLES, texturedModel.model.vertexCount, GL11.GL_UNSIGNED_INT, 0)


        GL20.glDisableVertexAttribArray(1)
        GL20.glDisableVertexAttribArray(0)
        GL30.glBindVertexArray(0)
    }

    fun renderRawModel(model: RawModel) {

        GL30.glBindVertexArray(model.vaoId)
        GL20.glEnableVertexAttribArray(0)

//        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.vertexCount)
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.vertexCount, GL11.GL_UNSIGNED_INT, 0)

        GL20.glDisableVertexAttribArray(0)
        GL30.glBindVertexArray(0)
    }
}