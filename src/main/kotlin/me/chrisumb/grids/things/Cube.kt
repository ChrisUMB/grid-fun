package me.chrisumb.grids.things

import me.chrisumb.grids.Engine
import me.chrisumb.grids.render.*
import me.chrisumb.grids.render.draw.Drawable
import me.chrisumb.grids.render.texture.Texture
import org.joml.Matrix4f
import org.joml.Quaternionf
import org.joml.Vector3f
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15C

data class Cube(
    val position: Vector3f = Vector3f(),
    val rotation: Quaternionf = Quaternionf()
) : Drawable {

    var texture: Texture? = null

    override fun draw() {
        val camera = Engine.camera
        texture?.bind()
        shaderProgram.bind()
        shaderProgram["uViewProjection"] = camera.viewProjection
        shaderProgram["uModel"] = Matrix4f().translate(position).rotate(rotation)

        vao.bind()
        vbo.bind()
        ibo.bind()
        vao.drawElements(36, GL11.GL_TRIANGLES, ibo)
    }

    companion object {
        val shaderProgram =
            ShaderProgram(Shader("basic.fs", Shader.Type.FRAGMENT), Shader("basic.vs", Shader.Type.VERTEX))

        val vbo = GLBuffer().data(
            floatArrayOf(
                1f, 1f, -1f, 1f, 0f,
                1f, -1f, -1f, 0f, 0f,
                1f, 1f, 1f, 1f, 1f,
                1f, -1f, 1f, 0f, 1f,

                -1f, 1f, -1f, 1f, 0f,
                -1f, -1f, -1f, 0f, 0f,
                -1f, 1f, 1f, 1f, 1f,
                -1f, -1f, 1f, 0f, 1f
            )
        )

        val ibo = GLBuffer(GL15C.GL_ELEMENT_ARRAY_BUFFER).data(
            intArrayOf(
                0, 2, 4,
                3, 7, 2,
                7, 5, 6,
                5, 7, 1,
                1, 3, 0,
                5, 1, 4,
                2, 6, 4,
                7, 6, 2,
                5, 4, 6,
                7, 3, 1,
                3, 2, 0,
                1, 0, 4
            )
        )

        val vao = VertexArray(GLType.FLOAT * 3, GLType.FLOAT * 2).apply {
            buffer = vbo
        }
    }
}