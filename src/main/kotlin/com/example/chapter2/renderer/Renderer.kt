package com.example.chapter2.renderer

import com.example.spookycopengl.graphic.Window
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30

class Renderer(private val window: Window) {

    fun prepare() {
        window.clear()
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