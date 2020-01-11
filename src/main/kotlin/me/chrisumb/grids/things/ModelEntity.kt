package me.chrisumb.grids.things

import me.chrisumb.grids.Engine
import me.chrisumb.grids.asset.Asset
import me.chrisumb.grids.render.Shader
import me.chrisumb.grids.render.ShaderProgram
import me.chrisumb.grids.render.draw.Drawable
import me.chrisumb.grids.render.texture.Texture
import org.joml.Matrix4f
import org.joml.Quaternionf
import org.joml.Vector3f

class ModelEntity(val asset: Asset) : Drawable {

    var position = Vector3f()
    var rotation = Quaternionf()
    var scale = Vector3f()

    var texture: Texture? = null
    var shader = ShaderProgram(
        Shader("wavefront/wavefront.vs", Shader.Type.VERTEX),
        Shader("wavefront/wavefront.fs", Shader.Type.FRAGMENT)
    )

    private val wavefront =
        WavefrontModel[asset] ?: throw IllegalArgumentException("Could not parse $asset as a wavefront model.")

    override fun draw() {
        val camera = Engine.camera
        texture?.bind()
        shader.bind()
        shader["uViewProjection"] = camera.viewProjection
        shader["uModel"] = Matrix4f().rotate(rotation).translate(position).scale(scale)
        wavefront.draw()
    }

}