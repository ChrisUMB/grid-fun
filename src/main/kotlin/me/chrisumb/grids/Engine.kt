package me.chrisumb.grids

import me.chrisumb.grids.render.draw.Drawable
import me.chrisumb.grids.time.Time
import org.joml.Quaternionf
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.glfwInit
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL13
import org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP
import org.lwjgl.opengl.GL32.GL_TEXTURE_CUBE_MAP

object Engine {

    lateinit var window: Window
        private set

    val drawables = ArrayList<Drawable>()

    var started = false
        private set

    var voidColor = Vector3f(.25f, .25f, 1f)
    val camera by lazy { Camera(Vector3f(), Quaternionf(), 70f, window) }

    fun start(width: Int, height: Int, title: String, init: Engine.() -> Unit) {
        if (started) {
            return
        }

        if (!glfwInit()) {
            throw RuntimeException("GLFW failed to initialize.")
        }

        window = Window(width, height, title)

        GL.createCapabilities()
        glClearColor(voidColor.x, voidColor.y, voidColor.z, 1f)

        glEnable(GL_CULL_FACE)
        glFrontFace(GL_CW)
        glEnable(GL_DEPTH_TEST)
        glEnable(GL_DEPTH_CLAMP)

        apply(init)
        while (!window.shouldClose) {
            Time.handlePreRender()
            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
            render()
            GLFW.glfwPollEvents()
        }


        started = true
    }

    fun render() {
        camera.onFrame()
        drawables.forEach { it.draw() }
        window.input.update()
        window.swapBuffers()
    }
}