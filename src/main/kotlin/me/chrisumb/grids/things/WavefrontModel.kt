package me.chrisumb.grids.things

import me.chrisumb.grids.WavefrontLoader
import me.chrisumb.grids.asset.Asset
import me.chrisumb.grids.render.GLBuffer
import me.chrisumb.grids.render.GLType
import me.chrisumb.grids.render.VertexArray
import me.chrisumb.grids.util.glCall
import org.joml.Vector2f
import org.joml.Vector3f
import org.lwjgl.opengl.GL11C

class WavefrontModel(private val ibo: GLBuffer, private val vbo: GLBuffer, private val vertexCount: Int) {

    fun bind() {
        vao.bind()
        vbo.bind()
        vao.buffer = vbo
        ibo.bind()
    }

    data class Vertex(
        val position: Vector3f,
        val texCoord: Vector2f,
        val normal: Vector3f
    )

    fun draw() {
        bind()
        glCall { vao.drawElements(vertexCount, GL11C.GL_TRIANGLES, ibo) }
    }

    companion object {

        private val cache = HashMap<Asset, WavefrontModel>()

        operator fun get(asset: Asset): WavefrontModel? {
            return cache.getOrPut(asset) {
                WavefrontLoader.parse(asset)
            }
        }

        private val vao = VertexArray(GLType.FLOAT * 3, GLType.FLOAT * 2, GLType.FLOAT * 3)

    }
}