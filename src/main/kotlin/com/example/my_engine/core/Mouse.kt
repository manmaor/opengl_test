package com.example.my_engine.core

import com.example.my_engine.graphic.Window
import com.example.my_engine.math.minus
import org.joml.Vector2f
import org.lwjgl.glfw.GLFW.*

class Mouse(
    private val window: Window
) {

    var prevPosition: Vector2f = Vector2f(-1f, -1f)
        private set
    var currentPosition: Vector2f = Vector2f()
        private set
    var displVec: Vector2f = Vector2f()
        private set

    var isLeftButtonPressed: Boolean = false
        private set
    var isRightButtonPressed: Boolean = false
        private set

    var inWindow: Boolean = false
        private set

    init {
        window.onCursorMoved = { x,y ->
//            println("${x}, ${y}")
            currentPosition = Vector2f(x.toFloat(), y.toFloat())
//            println("inside ${currentPosition.x}, ${currentPosition.y}")
        }
        window.onCursorEnteredWindow = { entered ->
            inWindow = entered
        }
        window.onMouseButtonPressed = { button, action, mode ->
            isLeftButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS
            isRightButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS
        }
    }

    fun input() {
//        val current = currentPosition
        displVec = Vector2f()
        if (inWindow) {
            val delta = currentPosition - prevPosition
            displVec = Vector2f(delta.y, delta.x)
        }
//        if (prevPosition.x > 0 && prevPosition.y > 0 && inWindow) {
//            val delta = current - prevPosition
//            displVec = Vector2f(delta.y, delta.x)
//        }
        prevPosition = Vector2f(currentPosition)
    }
}