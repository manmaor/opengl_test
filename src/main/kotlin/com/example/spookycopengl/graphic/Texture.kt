package com.example.spookycopengl.graphic

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL13
import org.lwjgl.stb.STBImage
import org.lwjgl.system.MemoryStack
import java.nio.ByteBuffer

class Texture(
    val id: Int = GL11.glGenTextures(),
    val width: Int,
    val height: Int
) {
    fun bind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id)
    }

    fun setParameter(param: TextureParameter, value: Int) {
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, param.name, value)
    }

    fun uploadData(width: Int, height: Int, data: ByteBuffer) {
        uploadData(GL11.GL_RGBA8, width, height, GL11.GL_RGBA, data)
    }

    fun uploadData(internalFormat: Int, width: Int, height: Int, format: Int, data: ByteBuffer) {
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL11.GL_UNSIGNED_BYTE, data)
    }

    fun delete() {
        GL11.glDeleteTextures(id)
    }

    companion object {
        fun createTexture(width: Int, height: Int, data: ByteBuffer) = Texture(width = width, height = height).apply {
            bind()

            setParameter(TextureParameter.WrapS, GL13.GL_CLAMP_TO_BORDER)
            setParameter(TextureParameter.WrapT, GL13.GL_CLAMP_TO_BORDER)
            setParameter(TextureParameter.MinFilter, GL13.GL_NEAREST)
            setParameter(TextureParameter.MagFilter, GL13.GL_NEAREST)

            uploadData(width, height, data)
        }

        fun loadTexture(path: String): Texture {
            return MemoryStack.stackPush().use { stack ->
                val w = stack.mallocInt(1)
                val h = stack.mallocInt(1)
                val comp = stack.mallocInt(1)


//                STBImage.stbi_set_flip_vertically_on_load(true)
                val image  = STBImage.stbi_load(Texture::class.java.classLoader.getResource(path)!!.path, w, h, comp, 4)
                    ?: throw Error("Failed to load a texture file! ${System.lineSeparator()} ${STBImage.stbi_failure_reason()}")

                createTexture(w.get(), h.get(), image)
            }
        }
    }


}

sealed class TextureParameter(val name: Int) {
    object WrapS: TextureParameter(GL11.GL_TEXTURE_WRAP_S)
    object WrapT: TextureParameter(GL11.GL_TEXTURE_WRAP_T)
    object MinFilter: TextureParameter(GL11.GL_TEXTURE_MIN_FILTER)
    object MagFilter: TextureParameter(GL11.GL_TEXTURE_MAG_FILTER)
}