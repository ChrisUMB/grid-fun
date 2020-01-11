package me.chrisumb.grids.render.texture

import me.chrisumb.grids.asset.Asset
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11C
import org.lwjgl.opengl.GL12C
import org.lwjgl.opengl.GL13C.GL_CLAMP_TO_BORDER
import org.lwjgl.opengl.GL14C
import java.awt.image.BufferedImage
import java.nio.ByteBuffer

abstract class Texture(val asset: Asset, val target: Int) {

    val id: Int = GL11C.glGenTextures()

    var filtering = Filtering.NEAREST
        set(value) {
            field = value
            bind()
            updateFiltering()
        }

    var wrapping = Wrapping.REPEAT
        set(value) {
            field = value
            bind()
            updateWrapping()
        }

    open fun bind() {
        GL11C.glBindTexture(target, id)
    }

    protected open fun updateFiltering() {
        GL11C.glTexParameteri(target, GL11C.GL_TEXTURE_MAG_FILTER, filtering.id)
        GL11C.glTexParameteri(target, GL11C.GL_TEXTURE_MIN_FILTER, filtering.id)
    }

    protected open fun updateWrapping() {
        GL11C.glTexParameteri(target, GL11C.GL_TEXTURE_WRAP_S, wrapping.id)
        GL11C.glTexParameteri(target, GL11C.GL_TEXTURE_WRAP_T, wrapping.id)
    }

    enum class Filtering(val id: Int) {
        LINEAR(GL11C.GL_LINEAR),
        NEAREST(GL11C.GL_NEAREST)
    }

    enum class Wrapping(val id: Int) {
        REPEAT(GL11C.GL_REPEAT),
        MIRRORED_REPEAT(GL14C.GL_MIRRORED_REPEAT),
        CLAMP_TO_EDGE(GL12C.GL_CLAMP_TO_EDGE),
        CLAMP_TO_BORDER(GL_CLAMP_TO_BORDER)
    }

    companion object {

        fun imgToBuffer(image: BufferedImage): ByteBuffer {
            val width = image.width
            val height = image.height
            val buffer = BufferUtils.createByteBuffer((width * height) * 4)
            for (y in 0 until height) {
                for (x in 0 until width) {
                    val argb = image.getRGB(x, y)
//                    val a = (argb shr 24) and 0xFF
//                    val rgba = (argb shl 8) or a
                    buffer.putInt(argb)
                }
            }

            buffer.flip()
            return buffer
        }
    }
}