package com.example.thin_matrix

import com.example.my_engine.core.Engine
import com.example.my_engine.core.Mouse
import com.example.my_engine.graphic.*
import com.example.my_engine.scene.Entity
import com.example.my_engine.scene.EntityId
import com.example.my_engine.scene.lights.AmbientLight
import com.example.my_engine.scene.lights.DirLight
import com.example.my_engine.scene.lights.SceneLights
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW.*

class ThinMatrixGame: Engine() {

    private val meshes = mutableListOf<Mesh>()
    private val textures = mutableListOf<Texture>()

    init {
        val vertices = arrayListOf(
            -0.5f,0.5f,-0.5f,
            -0.5f,-0.5f,-0.5f,
            0.5f,-0.5f,-0.5f,
            0.5f,0.5f,-0.5f,

            -0.5f,0.5f,0.5f,
            -0.5f,-0.5f,0.5f,
            0.5f,-0.5f,0.5f,
            0.5f,0.5f,0.5f,

            0.5f,0.5f,-0.5f,
            0.5f,-0.5f,-0.5f,
            0.5f,-0.5f,0.5f,
            0.5f,0.5f,0.5f,

            -0.5f,0.5f,-0.5f,
            -0.5f,-0.5f,-0.5f,
            -0.5f,-0.5f,0.5f,
            -0.5f,0.5f,0.5f,

            -0.5f,0.5f,0.5f,
            -0.5f,0.5f,-0.5f,
            0.5f,0.5f,-0.5f,
            0.5f,0.5f,0.5f,

            -0.5f,-0.5f,0.5f,
            -0.5f,-0.5f,-0.5f,
            0.5f,-0.5f,-0.5f,
            0.5f,-0.5f,0.5f
        ).toFloatArray()

        val indices = arrayListOf(
            0,1,3,
            3,1,2,
            4,5,7,
            7,5,6,
            8,9,11,
            11,9,10,
            12,13,15,
            15,13,14,
            16,17,19,
            19,17,18,
            20,21,23,
            23,21,22
        ).toIntArray()

        val textureCoords = arrayListOf(
            0f,0f,
            0f,1f,
            1f,1f,
            1f,0f,
            0f,0f,
            0f,1f,
            1f,1f,
            1f,0f,
            0f,0f,
            0f,1f,
            1f,1f,
            1f,0f,
            0f,0f,
            0f,1f,
            1f,1f,
            1f,0f,
            0f,0f,
            0f,1f,
            1f,1f,
            1f,0f,
            0f,0f,
            0f,1f,
            1f,1f,
            1f,0f
        ).toFloatArray()

//        val mesh = Mesh.load(vertices, textureCoords, indices)
//        meshes.add(mesh)
//        val texture = Texture.loadTexture("hello_world.png")
//        textures.add(texture)
//        val material = Material(texture, listOf(mesh))
//        val model = Model(ModelId("cube-model"), listOf(material))
//        val entity = Entity(id = EntityId("cube_entity"), model = model, position = Vector3f(0f,0f, -2f))


//        val model = Model.loadModel(ModelId("barrel"), "barrel/Barrel.obj", "barrel/Barrel.png")
//        val entity = Entity(id = EntityId("barrel"), model = model, position = Vector3f(0f,0f, -2f))
//
//        scene.addEntity(entity)

        val model = Model.loadModel(ModelId("box"), "box/box.obj", "box/box.png")
        val entity = Entity(id = EntityId("box"), model = model, position = Vector3f(0f,0f, 0f))

        scene.addEntity(entity)

        scene.lights = SceneLights(
            AmbientLight(intensity = 0.0f),
            DirLight(
                color = Vector3f(1f),
                direction = Vector3f(0f, 0f, 0f),
                intensity = 1f
            ),
            emptyList(),
            emptyList()
        )
    }

    override fun input(delta: Long) {
        val move = delta * MOVEMENT_SPEED
        val camera = scene.camera

        if (window.isKeyPressed(GLFW_KEY_W)) {
            camera.moveForward(move)
        }
        if (window.isKeyPressed(GLFW_KEY_S)) {
            camera.moveBackwards(move)
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            camera.moveLeft(move)
        }
        if (window.isKeyPressed(GLFW_KEY_D)) {
            camera.moveRight(move)
        }

        if (window.isKeyPressed(GLFW_KEY_I)) {
//            camera.moveUp(move)
            val entity = scene.modelEntitiesMap.values.first().first()
            entity.increasePosition(0f, 0f, -0.02f)
        }
        if (window.isKeyPressed(GLFW_KEY_K)) {
//            camera.moveDown(move)
            val entity = scene.modelEntitiesMap.values.first().first()
            entity.increasePosition(0f, 0f, 0.02f)
        }

        if (window.isKeyPressed(GLFW_KEY_L)) {
//            camera.moveDown(move)
            val entity = scene.modelEntitiesMap.values.first().first()
            entity.increasePosition(0.02f, 0f, 0f)
        }

        if (window.isKeyPressed(GLFW_KEY_J)) {
//            camera.moveDown(move)
            val entity = scene.modelEntitiesMap.values.first().first()
            entity.increasePosition(-0.02f, 0f, 0f)
        }

        if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            camera.addRotationDeg(0f, -move * 8)
        }
        if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            camera.addRotationDeg(0f, move * 8)
        }
        if (window.isKeyPressed(GLFW_KEY_UP)) {
            camera.addRotationDeg(-move * 8, 0f)
        }
        if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            camera.addRotationDeg(move * 8, 0f)
        }

        if(mouse.isLeftButtonPressed) {
            camera.addRotationDeg(-mouse.displVec.x * MOUSE_SENSITIVITY, -mouse.displVec.y * MOUSE_SENSITIVITY )
        }

//        val mouseInput = window.getMouseInput()
    }

    override fun update(delta: Long) {
//        val entity = scene.modelEntitiesMap.values.first().first()
//        entity.increasePosition(0f, 0f, -0.02f)
//        val z = entity.position.z
//        entity.position = Vector3f(mouse.currentPosition.x, -mouse.currentPosition.y, z)
//        entity.increaseRotation(0f, 1f, 0f)


    }

    override fun delete() {
        meshes.forEach { it.delete() }
        textures.forEach { it.delete() }
    }

    companion object {
        private const val MOUSE_SENSITIVITY = 0.1f
        private const val MOVEMENT_SPEED = 0.005f
    }

}