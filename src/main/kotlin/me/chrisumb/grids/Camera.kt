package me.chrisumb.grids

import me.chrisumb.grids.input.Key
import me.chrisumb.grids.input.MouseButton
import me.chrisumb.grids.time.Time
import org.joml.Matrix4f
import org.joml.Quaternionf
import org.joml.Vector2f
import org.joml.Vector3f

class Camera(
    var position: Vector3f,
    var rotation: Quaternionf,
    fov: Float,
    val window: Window
) {

    val aspectRatio get() = window.width / window.height.toFloat()

    var projectionMatrix = Matrix4f().setPerspective(Math.toRadians(fov.toDouble()).toFloat(), aspectRatio, 0.1f, 1000f)
        private set

    var viewProjection = Matrix4f()
        private set

    var fov = fov
        set(value) {
            projectionMatrix = Matrix4f().setPerspective(Math.toRadians(value.toDouble()).toFloat(), aspectRatio, 0.1f, 1000f)
            field = value
        }

    val lastMousePosition = Vector2f()

    fun onFrame() {
        //Rotate inverse, translate inverse
        val input = window.input
        if (!window.isMouseEngaged && input[MouseButton.LEFT].isPressed) {
            window.isMouseEngaged = true
            lastMousePosition.set(input.mousePosition)
        }

        if (window.isMouseEngaged && input[Key.ESCAPE].isPressed) {
            window.isMouseEngaged = false
        }

        val vec = Vector3f()
        val moveSpeed = 10f

        if (input[Key.W].isHeld) {
            vec.z -= 1f
        }

        if (input[Key.S].isHeld) {
            vec.z += 1f
        }

        if (input[Key.A].isHeld) {
            vec.x -= 1f
        }

        if (input[Key.D].isHeld) {
            vec.x += 1f
        }

        if (input[Key.SPACE].isHeld) {
            position.y -= moveSpeed * Time.renderDeltaTime
        }

        if (input[Key.LEFT_SHIFT].isHeld) {
            position.y += moveSpeed * Time.renderDeltaTime
        }
//
        if (vec.x != 0f || vec.y != 0f || vec.z != 0f) {
            vec.normalize().rotate(Quaternionf(rotation).conjugate()).mul(Time.renderDeltaTime * moveSpeed)
            position.sub(vec)
        }

        if (window.isMouseEngaged) {
            val mousePosition = input.mousePosition
            val difference = Vector2f(mousePosition).sub(lastMousePosition)
            lastMousePosition.set(mousePosition)

            val sensMousePosition = difference.mul(0.11f * 0.015f)
            if (sensMousePosition.x != 0f) {
                rotation.rotateAxis(sensMousePosition.x, Vector3f(0f, 1f, 0f))
            }

            if (sensMousePosition.y != 0f) {
                rotation.rotateAxis(sensMousePosition.y, rotation.positiveX(Vector3f()))
            }
        }

        viewProjection = Matrix4f(projectionMatrix).rotate(rotation).translate(Vector3f(position))
    }

    fun setProjection() {
        projectionMatrix = Matrix4f().setPerspective(Math.toRadians(fov.toDouble()).toFloat(), aspectRatio, 0.1f, 1000f)
    }
}
