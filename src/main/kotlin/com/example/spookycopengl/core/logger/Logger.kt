package com.example.spookycopengl.core.logger


enum class Level(private val tag: String) {
    INFO("I"),
    DEBUG("D"),
    WARNING("W");

    override fun toString(): String {
        return tag
    }
}

object Log {

    fun i(tag: String, message: String) {
        println("${Level.INFO}: $tag: $message")
    }

    fun d(tag: String, message: String) {
        println("${Level.DEBUG}: $tag: $message")
    }

    fun w(tag: String, message: String) {
        println("${Level.WARNING}: $tag: $message")
    }

}