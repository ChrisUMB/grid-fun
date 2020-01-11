package me.chrisumb.grids.render

import me.chrisumb.grids.util.glCall
import org.joml.Matrix4f
import org.joml.Vector2fc
import org.joml.Vector3fc
import org.joml.Vector4fc
import org.lwjgl.opengl.GL20C.*
import org.lwjgl.opengl.GL30C.glBindFragDataLocation
import org.lwjgl.system.MemoryStack

class ShaderProgram(vararg shaders: Shader) {

    val id = glCreateProgram()

    private val uniformMap = HashMap<String, Int>()

    init {
        for (shader in shaders) {
            glAttachShader(id, shader.id)
        }

        glBindFragDataLocation(id, 0, "fColor")
        glLinkProgram(id)

        if (glGetProgrami(id, GL_LINK_STATUS) == 0) {
            throw IllegalStateException("Error Linking: \n${glGetProgramInfoLog(id)}")
        }

        glValidateProgram(id)

        if (glGetProgrami(id, GL_VALIDATE_STATUS) == 0) {
            throw IllegalStateException("Error Validating: \n${glGetProgramInfoLog(id)}")
        }

        for (shader in shaders) {
            glDetachShader(id, shader.id)
        }
    }

    private fun getUniformLocation(name: String): Int {
        return uniformMap.computeIfAbsent(name) { glGetUniformLocation(id, name) }
    }

    operator fun set(name: String, value: Float) {
        glCall { glUniform1f(getUniformLocation(name), value) }
    }

    operator fun set(name: String, value: Vector2fc) {
        glCall { glUniform2f(getUniformLocation(name), value.x(), value.y()) }
    }

    operator fun set(name: String, value: Vector3fc) {
        glCall { glUniform3f(getUniformLocation(name), value.x(), value.y(), value.z()) }
    }

    operator fun set(name: String, value: Vector4fc) {
        glCall { glUniform4f(getUniformLocation(name), value.x(), value.y(), value.z(), value.w()) }
    }

    operator fun set(name: String, value: Matrix4f) {
        MemoryStack.stackPush().use {
            glCall { glUniformMatrix4fv(getUniformLocation(name), false, value.getTransposed(it.mallocFloat(16))) }
        }
    }

    operator fun set(name: String, value: Int) {
        glCall { glUniform1i(getUniformLocation(name), value) }
    }

    operator fun set(name: String, value: Boolean) {
        this[name] = if (value) 1 else 0
    }

    operator fun set(name: String, value: Color) {
        this[name] = value.toVector4f()
    }

    fun bind() {
        glCall { glUseProgram(id) }
    }

}