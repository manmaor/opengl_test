package com.example.my_engine.graphic.imgui

import com.example.my_engine.graphic.Window
import com.example.my_engine.scene.Scene

interface IImGuiInstance {
    fun drawGui()

    fun handleGuiInput(scene: Scene, window: Window) : Boolean
}