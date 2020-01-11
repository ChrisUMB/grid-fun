package me.chrisumb.grids.input

import me.chrisumb.grids.Window
import org.joml.Vector2f
import org.joml.Vector2fc
import org.lwjgl.glfw.GLFW
import java.util.*

class Input(private val window: Window) {

    private val states: MutableMap<Button?, ButtonState> = HashMap()
    private val _mousePosition = Vector2f()

    val mousePosition: Vector2fc
        get() = _mousePosition

    var textBuffer = StringBuilder()
        private set

    var scrollDelta = 0f
        private set

    var mouseMoved = false
        private set

    operator fun get(button: Button): ButtonState {
        return states.getOrPut(button) { ButtonState(button) }
    }

    fun update() {
        textBuffer = StringBuilder()
        for (state in states.values) {
            state.update()
        }

        mouseMoved = false
        scrollDelta = 0f
    }

    init {
        GLFW.glfwSetKeyCallback(
            window.id
        ) { w: Long, key: Int, scanCode: Int, action: Int, mods: Int ->
            if (action != GLFW.GLFW_PRESS && action != GLFW.GLFW_RELEASE) return@glfwSetKeyCallback
            val state = get(Key[key] ?: return@glfwSetKeyCallback)
            when (action) {
                GLFW.GLFW_PRESS -> state.pressed()
                GLFW.GLFW_RELEASE -> state.released()
            }
        }

        GLFW.glfwSetMouseButtonCallback(
            window.id
        ) { w: Long, button: Int, action: Int, mods: Int ->
            if (action != GLFW.GLFW_PRESS && action != GLFW.GLFW_RELEASE) return@glfwSetMouseButtonCallback
            val state = get(MouseButton[button] ?: return@glfwSetMouseButtonCallback)
            when (action) {
                GLFW.GLFW_PRESS -> state.pressed()
                GLFW.GLFW_RELEASE -> state.released()
            }
        }

        GLFW.glfwSetCursorPosCallback(window.id) { w: Long, x: Double, y: Double ->
            _mousePosition.set(x.toFloat(), y.toFloat())
            mouseMoved = true
        }

        GLFW.glfwSetScrollCallback(
            window.id
        ) { w: Long, x: Double, y: Double -> scrollDelta = y.toFloat() }

        GLFW.glfwSetCharCallback(
            window.id
        ) { w: Long, codePoint: Int -> textBuffer.appendCodePoint(codePoint) }
    }
}