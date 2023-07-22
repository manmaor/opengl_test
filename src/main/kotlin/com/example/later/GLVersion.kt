package com.example.later

import org.lwjgl.opengl.*;

class GLVersion {

    private val versionString: String
    private val majorVersion: Int
    private val minorVersion: Int
    private val version: Double
    private val extensionMap: Map<String, Boolean>

//    fun isExtensionSupported(extension: GLExtension )
//            = isExtensionSupported(extension.asString())

    fun isExtensionSupported(extension: String): Boolean = extensionMap.containsKey(extension)

    fun getSupportedExtensions(): Set<String> = extensionMap.keys

    init {
        versionString = GL11.glGetString(GL11.GL_VERSION)!!
        val majorVersionIndex = versionString.indexOf('.')
        var minorVersionIndex = majorVersionIndex + 1

        while (minorVersionIndex < versionString.length && Character.isDigit(minorVersionIndex)) {
            minorVersionIndex++
        }
        minorVersionIndex++

        majorVersion = Integer.parseInt(versionString.substring(0, majorVersionIndex))
        minorVersion = Integer.parseInt(versionString.substring(majorVersionIndex + 1, minorVersionIndex))
        version = (versionString.substring(0, minorVersionIndex)).toDouble()

        val supportedExtensions = mutableListOf<String?>()
        if (majorVersion >= 3) {
            val numExtensions = GL11.glGetInteger(GL30.GL_NUM_EXTENSIONS)

            for (i in 0..numExtensions) {
                supportedExtensions.add(GL30.glGetStringi(GL11.GL_EXTENSIONS, i))
            }
        } else {
            val extensionsAsString = GL11.glGetString(GL11.GL_EXTENSIONS)
            supportedExtensions.addAll(extensionsAsString!!.split(" "))
        }

        extensionMap = hashMapOf()
        supportedExtensions.filterNotNull().forEach { extension ->
            extensionMap.put(extension, true)
        }

    }
}