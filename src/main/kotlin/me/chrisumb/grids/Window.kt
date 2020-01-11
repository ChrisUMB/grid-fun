package me.chrisumb.grids

import me.chrisumb.grids.input.Input
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL11C

class Window(
    width: Int,
    height: Int,
    val title: String
) {

    var width = width
        private set
    var height = height
        private set

    val id: Long

    val input: Input

    var isMouseEngaged = false
        set(value) {
            field = value
            glfwSetInputMode(id, GLFW_CURSOR, if (value) GLFW_CURSOR_DISABLED else GLFW_CURSOR_NORMAL)
        }

    val shouldClose: Boolean
        get() = glfwWindowShouldClose(id)

    init {
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3)
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL11C.GL_TRUE)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
        id = glfwCreateWindow(width, height, title, 0, 0)
        if (id == 0L) {
            throw RuntimeException("Failed to create window.")
        }


        input = Input(this)
        glfwSetWindowSizeCallback(id, ::resizeCallback)
        glfwMakeContextCurrent(id)
        glfwShowWindow(id)
    }

    fun swapBuffers() {
        glfwSwapBuffers(id)
    }

    private fun resizeCallback(id: Long, width: Int, height: Int) {
        GL11C.glViewport(0, 0, width, height)
        this.width = width
        this.height = height
        Engine.camera.setProjection()
        Engine.render()
    }
}