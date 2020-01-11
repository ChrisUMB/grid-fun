package me.chrisumb.grids.render

import org.lwjgl.opengl.GL20C.*
import org.lwjgl.opengl.GL32C.GL_GEOMETRY_SHADER
import java.io.Reader

class Shader(val name: String, val type: Type) {

    enum class Type(val id: Int) {
        FRAGMENT(GL_FRAGMENT_SHADER),
        VERTEX(GL_VERTEX_SHADER),
        GEOMETRY(GL_GEOMETRY_SHADER)
    }

    val id = glCreateShader(type.id)

    init {
        val resource = ClassLoader.getSystemResourceAsStream("shaders/$name")
            ?: throw IllegalArgumentException("No valid source for shader at 'shader/$name'")

        val code = resource.reader().use(Reader::readText)
        glShaderSource(id, code)
        glCompileShader(id)

        if (glGetShaderi(id, GL_COMPILE_STATUS) == 0) {
            val log = glGetShaderInfoLog(id)
            throw IllegalStateException("ERROR: Shader Compile Failure (shaders/$name) \n$log")
        }
    }

    fun delete() {
        glDeleteShader(id)
    }

}