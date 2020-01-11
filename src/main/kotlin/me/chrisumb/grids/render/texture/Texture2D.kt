package me.chrisumb.grids.render.texture

import me.chrisumb.grids.asset.Asset
import me.chrisumb.grids.util.toByteBuffer
import org.lwjgl.opengl.GL11C.*

class Texture2D(asset: Asset) : Texture(asset, GL_TEXTURE_2D) {

    val image = asset.readImage()
    val width = image.width
    val height = image.height

    init {
        bind()
        updateFiltering()
        updateWrapping()
        glTexImage2D(target, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image.toByteBuffer())
    }
}