package com.example.chapter1

import org.lwjgl.*
import org.lwjgl.glfw.*
import org.lwjgl.glfw.Callbacks.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.*
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL12.*
import org.lwjgl.opengl.GL13.*
import org.lwjgl.opengl.GL14.*
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL21.*
import org.lwjgl.opengl.GL30.*
import org.lwjgl.opengl.GL31.*
import org.lwjgl.opengl.GL32.*
import org.lwjgl.opengl.GL33.*
import org.lwjgl.opengl.GL40.*
import org.lwjgl.opengl.GL41.*
import org.lwjgl.opengl.GL42.*
import org.lwjgl.opengl.GL43.*
import org.lwjgl.opengl.GL44.*
import org.lwjgl.opengl.GL45.*
import org.lwjgl.opengl.GL46.*
import org.lwjgl.system.*
import org.lwjgl.system.MemoryStack.*
import org.lwjgl.system.MemoryUtil.*
import java.nio.*


object Test {

    fun main() {

        if (!glfwInit()) {
            println("GLFW not init.")
            return
        }

        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1)
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
        val window = glfwCreateWindow(500, 500, "Window", NULL, NULL);
        if (window == NULL) {
            println("Window not create.");
            return;
        }

        glfwMakeContextCurrent(window)
        glfwSwapInterval(1)
        glfwShowWindow(window)

        GL.createCapabilities()

//        Utilities.printGLInfo();

//        val vert = glCreateShader(GL_VERTEX_SHADER);
//        glShaderSource(vert, Utilities.loadStrFromFile("Shaders/shader.vert"));
//        glCompileShader(vert);
//        if(glGetShaderi(vert, GL_COMPILE_STATUS)==GL_FALSE) {
//            System.out.println("Vertex shader compilation error:\n"+glGetShaderInfoLog(vert));
//        }
//        int frag=glCreateShader(GL_FRAGMENT_SHADER);
//        glShaderSource(frag, Utilities.loadStrFromFile("Shaders/shader.frag"));
//        glCompileShader(frag);
//        if(glGetShaderi(frag, GL_COMPILE_STATUS)==GL_FALSE) {
//            System.out.println("Fragment shader compilation error:\n"+glGetShaderInfoLog(frag));
//        }

//        int prog=glCreateProgram();
//        glAttachShader(prog, vert);
//        glAttachShader(prog, frag);
//        glLinkProgram(prog);

        val vboData = floatArrayOf(
            0f,0f,0f,
            1f,0f,0f,
            0f,1f,0f
        )

        val buf = BufferUtils.createFloatBuffer(vboData.size)
        buf.put(vboData).flip()

        val vao = glGenVertexArrays()
        val vbo = glGenBuffers()


        glBindVertexArray(vao)

        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        glBufferData(GL_ARRAY_BUFFER, buf, GL_STATIC_DRAW)
        glEnableVertexAttribArray(0)
        glVertexAttribPointer(0, 3, GL_FLOAT, false,0 , 0); // 3*Float.BYTES

        glBindVertexArray(0)


//        println("prog=$prog,vert=$vert,frag=$frag");
        println("vao=$vao,vbo=$vbo");

        while (!glfwWindowShouldClose(window)) {
            glClearColor(1f, 0f, 0f, 1f)
            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

//            glUseProgram(prog);

            glBindVertexArray(vao);
//      glDrawElements(GL_TRIANGLES, indices);
            glDrawArrays(GL_TRIANGLES, 0, 3);
            glBindVertexArray(0);

//            glUseProgram(0);

            glfwSwapBuffers(window);
            glfwPollEvents();
        }

        glfwDestroyWindow(window);
        glfwTerminate();

    }

}
