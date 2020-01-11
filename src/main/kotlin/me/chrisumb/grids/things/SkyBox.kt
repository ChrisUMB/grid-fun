package me.chrisumb.grids.things

import me.chrisumb.grids.Engine
import me.chrisumb.grids.asset.Asset
import me.chrisumb.grids.render.*
import me.chrisumb.grids.render.draw.Drawable
import me.chrisumb.grids.render.texture.TextureCubeMap
import org.joml.Matrix4f
import org.lwjgl.opengl.GL11C
import org.lwjgl.opengl.GL11C.*
import org.lwjgl.opengl.GL15C.GL_ELEMENT_ARRAY_BUFFER

class SkyBox(val asset: Asset) : Drawable {

    val textureCubeMap = TextureCubeMap(asset)

    override fun draw() {
        glDisable(GL_DEPTH_TEST)
        glDepthMask(false)
        textureCubeMap.bind()
        shaderProgram.bind()
        val camera = Engine.camera
        val viewRotMat = Matrix4f().rotate(camera.rotation)
        shaderProgram["uProjection"] = camera.projectionMatrix
        shaderProgram["uViewRot"] = viewRotMat

        vao.bind()
        vbo.bind()
        ibo.bind()
        vao.drawElements(36, GL_TRIANGLES, ibo)

        glDepthMask(true)
        glEnable(GL_DEPTH_TEST)
    }

    companion object {
        val shaderProgram =
            ShaderProgram(
                Shader("skybox/skybox.fs", Shader.Type.FRAGMENT),
                Shader("skybox/skybox.vs", Shader.Type.VERTEX)
            )

        val vbo = GLBuffer().data(
            floatArrayOf(
                1f, 1f, -1f,
                1f, -1f, -1f,
                1f, 1f, 1f,
                1f, -1f, 1f,
                -1f, 1f, -1f,
                -1f, -1f, -1f,
                -1f, 1f, 1f,
                -1f, -1f, 1f
            )
        )

        val ibo = GLBuffer(GL_ELEMENT_ARRAY_BUFFER).data(
            intArrayOf(
                4, 2, 0,
                2, 7, 3,
                6, 5, 7,
                1, 7, 5,
                0, 3, 1,
                4, 1, 5,
                4, 6, 2,
                2, 6, 7,
                6, 4, 5,
                1, 3, 7,
                0, 2, 3,
                4, 0, 1
            )
        )

        val vao = VertexArray(GLType.FLOAT * 3).apply {
            buffer = vbo
        }
    }
}