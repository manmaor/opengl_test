package com.example.my_engine.graphic

import org.lwjgl.glfw.Callbacks.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.system.MemoryUtil.NULL

class Window {

    private val id: Long

    var vSync: Boolean
        set(value) {
            field = value
            glfwSwapInterval(if (value) 1 else 0)
        }

    var title: String = ""
        set(value) {
            field = value
            glfwSetWindowTitle(id, title)
        }

    var location: Pair<Int, Int> = Pair(0 , 0)
        set(value) {
            field = value
            glfwSetWindowPos(id, value.first, value.second)
        }

    private var _height = 0
    private var _width = 0

    var size: Pair<Int, Int>
        set(value) {
            glfwSetWindowSize(id, value.first, value.second)
        }
        get() = Pair(_width, _height)


    var onCursorMoved: ((x: Double, y: Double) -> Unit)? = null
    var onCursorEnteredWindow: ((entered: Boolean) -> Unit)? = null
    var onMouseButtonPressed: ((button: Int,action: Int, mode: Int) -> Unit)? = null
    private var onWindowResized: (() -> Unit)? = null


    constructor(
        width: Int,
        height: Int,
        title: String,
        vSync: Boolean = true,
        onResize: (() -> Unit)? = null
    ) {

        /* Creating a temporary window for getting the available OpenGL version */
//        glfwDefaultWindowHints()
//        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
//        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
//        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE)
//        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4)
//        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1)
//        val temp = glfwCreateWindow(1, 1, "", NULL, NULL)
//        glfwMakeContextCurrent(temp)
//        GL.createCapabilities()
//        val caps = GL.getCapabilities()
//        glfwDestroyWindow(temp)

        /*Reset and set windows hints*/
//        if (caps.OpenGL32) {
//            /* Hints for OpenGL 3.2 core profile */
//            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
//            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
//            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
//            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
//        } else if (caps.OpenGL21) {
//            /* Hints for legacy OpenGL 2.1 */
//            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 2);
//            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
//        } else {
//            throw RuntimeException("Neither OpenGL 3.2 nor OpenGL 2.1 is "
//                    + "supported, you may want to update your graphics driver.");
//        }

        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE) // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)

        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1)

        // add debug flag
//        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_DEBUG_CONTEXT, GLFW.GLFW_TRUE)

        /* Create window with specified OpenGL context */
        id = glfwCreateWindow(width, height, title, NULL, NULL)
        if (id == NULL) {
            glfwTerminate()
            throw RuntimeException("Failed to create the GLFW window")
        }

        // Center screen
        stackPush().use { stack ->
            val pWidth = stack.mallocInt(1)
            val pHeight = stack.mallocInt(1)

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(id, pWidth, pHeight)

            val videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor())!!

            // Center the window
            glfwSetWindowPos(
                id,
                (videoMode.width() - pWidth[0]) / 2,
                (videoMode.height() - pHeight[0]) / 2
            )
        }


        // exit key listener
        glfwSetKeyCallback(id) { window, key, scancode, action, mods ->
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true) // Will be detected in the rendering loop
            }
        }

        _width = width
        _height = height

        onWindowResized = onResize

        setCallbacks()

        glfwMakeContextCurrent(id)

        this.vSync = vSync
        this.title = title

        glfwShowWindow(id)
        GL.createCapabilities()
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        GL11.glViewport(0,0, width, height)
    }

    fun setCallbacks() {
        glfwSetCursorPosCallback(id) { _, x, y ->
            onCursorMoved?.invoke(x, y)
        }
        glfwSetCursorEnterCallback(id) { _, entered ->
            onCursorEnteredWindow?.invoke(entered)
        }
        glfwSetMouseButtonCallback(id) {_, button, action, mode ->
            onMouseButtonPressed?.invoke(button, action, mode)
        }

        glfwSetFramebufferSizeCallback(id) { _, width, height ->
            _width = width
            _height = height

            onWindowResized?.invoke()
        }
    }

    fun isClosing() = glfwWindowShouldClose(id)
    fun isKeyPressed(keyCode: Int) = glfwGetKey(id, keyCode) == GLFW_PRESS
//    fun getMouseInput() = glfwmouse

    fun update() {
        glfwSwapBuffers(id)
        glfwPollEvents()
    }

    fun clear() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
        GL11.glViewport(0,0, _width, _height)
    }

    fun dispose() {
        glfwFreeCallbacks(id)
        glfwDestroyWindow(id)
    }

}