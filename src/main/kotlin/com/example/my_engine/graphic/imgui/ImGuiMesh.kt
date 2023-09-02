package com.example.my_engine.graphic.imgui

import com.example.my_engine.graphic.VAO
import com.example.my_engine.graphic.VBO
import imgui.ImDrawData
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20


class ImGuiMesh(
    val vao: VAO = VAO(),
    val verticesVbo: VBO = VBO(),
    val indicesVbo: VBO = VBO()
) {

    init {
        vao.bind()

        verticesVbo.bind()
        GL20.glEnableVertexAttribArray(0)
        GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, ImDrawData.SIZEOF_IM_DRAW_VERT, 0)
        GL20.glEnableVertexAttribArray(1)
        GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, ImDrawData.SIZEOF_IM_DRAW_VERT, 8)
        GL20.glEnableVertexAttribArray(2)
        GL20.glVertexAttribPointer(2, 4, GL11.GL_UNSIGNED_BYTE, true, ImDrawData.SIZEOF_IM_DRAW_VERT, 16)

        verticesVbo.unbind()
        vao.unbind()

    }

    fun delete() {
        indicesVbo.delete()
        verticesVbo.delete()
        vao.delete()
    }
}