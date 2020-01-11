package me.chrisumb.grids.render.texture

import me.chrisumb.grids.asset.Asset
import org.lwjgl.opengl.GL11C.*

class Texture2D(asset: Asset) : Texture(asset, GL_TEXTURE_2D) {

    val image = asset.readImage()
    val width = image.width
    val height = image.height

    init {
        bind()
        glTexImage2D(target, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, imgToBuffer(image))
        updateFiltering()
        updateWrapping()
    }
}