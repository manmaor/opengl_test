package com.example.my_engine.graphic

class TextureManager {

    val textureMap = mutableMapOf<TexturePath, Texture>()

    fun delete() {
        textureMap.values.forEach(Texture::delete)
    }

    fun getTexture(path: String): Texture {
        return textureMap.getOrPut(TexturePath(path)) {
            Texture.loadTexture(path)
        }
    }

    companion object {

    }

}