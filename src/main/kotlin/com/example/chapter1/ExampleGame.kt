package com.example.chapter1

import com.example.chapter2.renderer.Loader
import com.example.chapter2.renderer.Renderer
import com.example.chapter2.renderer.RendererSimple
import org.lwjgl.Version
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.glfw.GLFWKeyCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryUtil


class HelloLWJGL {
    private var errorCallback: GLFWErrorCallback? = null
    private var keyCallback: GLFWKeyCallback? = null
    private var window: Long = 0
    private var sp = 0.0f
    private var swapcolor = false

    private fun init() {
//        GLFW.glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err).also { errorCallback = it })
        GLFWErrorCallback.createPrint(System.err).set()
        check(GLFW.glfwInit()) { "Unable to initialize GLFW" }

        GLFW.glfwDefaultWindowHints()
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE)
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE)

//        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE)
//        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE)
//        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 4)
//        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 1)

        GLFW.glfwWindowHint(GLFW.GLFW_DEPTH_BITS,GLFW.GLFW_TRUE)


        val WIDTH = 300
        val HEIGHT = 300
        window = GLFW.glfwCreateWindow(WIDTH, HEIGHT, "Hello LWJGL3", MemoryUtil.NULL, MemoryUtil.NULL)
        if (window == MemoryUtil.NULL) throw RuntimeException("Failed to create the GLFW window")
        GLFW.glfwSetKeyCallback(window, object : GLFWKeyCallback() {
            override fun invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
                if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) GLFW.glfwSetWindowShouldClose(
                    window,
                    true
                )
            }
        }.also { keyCallback = it })
        val vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor())
        GLFW.glfwSetWindowPos(window, (vidmode!!.width() - WIDTH) / 2, (vidmode.height() - HEIGHT) / 2)
        GLFW.glfwMakeContextCurrent(window)
        GLFW.glfwSwapInterval(1)
        GLFW.glfwShowWindow(window)
    }

    private fun update() {
//        sp = sp + 0.001f
//        if (sp > 1.0f) {
//            sp = 0.0f
//            swapcolor = !swapcolor
//        }

        sp = 0.5f
    }

    private fun render() {
        drawQuad()


    }

    private fun drawQuad() {
        if (!swapcolor) {
            GL11.glColor3f(0.0f, 1.0f, 0.0f)
        } else {
            GL11.glColor3f(0.0f, 0.0f, 1.0f)
        }
        GL11.glBegin(GL11.GL_QUADS)
        run {
            GL11.glVertex3f(-sp, -sp, 0.0f)
            GL11.glVertex3f(sp, -sp, 0.0f)
            GL11.glVertex3f(sp, sp, 0.0f)
            GL11.glVertex3f(-sp, sp, 0.0f)
        }
        GL11.glEnd()
    }

    private fun loop() {
        GL.createCapabilities()
        println("----------------------------")
        println("OpenGL Version : " + GL11.glGetString(GL11.GL_VERSION))
        println("OpenGL Max Texture Size : " + GL11.glGetInteger(GL11.GL_MAX_TEXTURE_SIZE))
        println("OpenGL Vendor : " + GL11.glGetString(GL11.GL_VENDOR))
        println("OpenGL Renderer : " + GL11.glGetString(GL11.GL_RENDERER))
        println("OpenGL Extensions supported by your card : ")
        val extensions = GL11.glGetString(GL11.GL_EXTENSIONS)
        println(extensions)
//        val extArr = extensions!!.split("\\ ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//        for (i in extArr.indices) {
//            println(extArr[i])
//        }


//        val vertices = arrayListOf(
//            -.5f, .5f, 0f,
//            -.5f, -.5f, 0f,
//            .5f, -.5f, 0f,
//            .5f, .5f, 0f
//        ).toFloatArray()
//
//        val indices = arrayListOf(
//            0,1,3,
//            3,1,2
//        ).toIntArray()
//
//        val model = Loader.loadToVAO(vertices, indices)


        println("----------------------------")
        while (!GLFW.glfwWindowShouldClose(window)) {
            if (!swapcolor) {
                GL11.glClearColor(0.0f, 0.0f, 1.0f, 0.0f)
            } else {
                GL11.glClearColor(0.0f, 1.0f, 0.0f, 0.0f)
            }
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
            update()
            render()
//
//            RendererSimple.render(model)

            GLFW.glfwSwapBuffers(window)
            GLFW.glfwPollEvents()
        }
    }

    fun run() {
        println("Hello LWJGL3 " + Version.getVersion() + "!")
        try {
            init()
            loop()
            GLFW.glfwDestroyWindow(window)
            keyCallback?.free()
        } finally {
            GLFW.glfwTerminate()
            errorCallback?.free()
        }
    }
}