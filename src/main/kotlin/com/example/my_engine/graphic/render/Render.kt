package com.example.my_engine.graphic.render

import com.example.my_engine.graphic.Window
import com.example.my_engine.scene.Scene
import org.lwjgl.opengl.GL11

class Render(val window: Window) {

    private val sceneRenderer = SceneRenderer()


    fun render(scene: Scene) {


        GL11.glEnable(GL11.GL_DEPTH_TEST);
        // glViewport(0, 0, window.getWidth(), window.getHeight());
        sceneRenderer.render(scene)
    }

    fun delete() {
        sceneRenderer.delete()
    }
}