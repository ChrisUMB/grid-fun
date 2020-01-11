package me.chrisumb.grids.render

import org.lwjgl.opengl.GL15C.*

class GLBuffer(val target: Int = GL_ARRAY_BUFFER) {

    val id = glGenBuffers()

    init {
        bind()
    }

    fun data(data: FloatArray): GLBuffer {
        glBufferData(target, data, GL_STATIC_DRAW)
        return this
    }

    fun data(data: IntArray): GLBuffer {
        glBufferData(target, data, GL_STATIC_DRAW)
        return this
    }

    fun bind() {
        glBindBuffer(target, id)
    }
}