package com.example.my_engine.graphic.render

import com.example.my_engine.graphic.Window
import com.example.my_engine.scene.Scene
import org.lwjgl.opengl.GL11

class Render(val window: Window) {

    private val sceneRenderer = SceneRenderer()

    fun render(scene: Scene) {

        GL11.glEnable(GL11.GL_DEPTH_TEST) // requires to know which vertex to draw above other

        // In OpenGL, by default, triangles that are in counter-clockwise order are facing towards the viewer and triangles that are in clockwise order are facing backwards.
        GL11.glEnable(GL11.GL_CULL_FACE) // disable faces that are in X direction.
        GL11.glCullFace(GL11.GL_BACK) // indicates which is the X direction

        // glViewport(0, 0, window.getWidth(), window.getHeight());
        sceneRenderer.render(scene)
    }

    fun delete() {
        sceneRenderer.delete()
    }
}