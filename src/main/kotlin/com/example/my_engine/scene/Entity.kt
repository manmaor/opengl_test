package com.example.my_engine.scene

import com.example.my_engine.graphic.Model
import org.joml.Matrix4f
import org.joml.Quaternionf
import org.joml.Vector3f

@JvmInline
value class EntityId(val value: String)

class Entity(
    val id: EntityId,
    val model: Model,
    var position: Vector3f = Vector3f(),
    val rotation: Quaternionf = Quaternionf(),
    val scale: Float = 1f
) {

    val modelMatrix: Matrix4f
        get() =
             Matrix4f().identity().translationRotateScale(position, rotation, scale)
//            Maths.createTransformationMatrix(position,rotation.x,rotation.y,rotation.z,scale)

    fun increasePosition(x: Float, y: Float, z: Float) {
        this.position.x += x
        this.position.y += y
        this.position.z += z
    }

    fun increaseRotation(x: Float, y: Float, z: Float) {
        rotation.rotate(
            Math.toRadians(x.toDouble()).toFloat(),
            Math.toRadians(y.toDouble()).toFloat(),
            Math.toRadians(z.toDouble()).toFloat()
        )
    }

    fun setRotation(x: Float, y: Float, z: Float, angle: Float) {
        rotation.fromAxisAngleRad(x, y, z, angle)
    }
}
