package me.chrisumb.grids.render.texture

import me.chrisumb.grids.asset.Asset
import me.chrisumb.grids.util.glCall
import me.chrisumb.grids.util.toByteBuffer
import org.lwjgl.opengl.GL11.glTexImage2D
import org.lwjgl.opengl.GL11C.*
import org.lwjgl.opengl.GL12C.GL_TEXTURE_WRAP_R
import org.lwjgl.opengl.GL13C
import org.lwjgl.opengl.GL13C.GL_MAX_CUBE_MAP_TEXTURE_SIZE
import org.lwjgl.opengl.GL13C.GL_TEXTURE_CUBE_MAP

class TextureCubeMap(asset: Asset) : Texture(asset, GL_TEXTURE_CUBE_MAP) {

    init {
        bind()
        wrapping = Wrapping.CLAMP_TO_EDGE
        updateFiltering()
//        updateWrapping()

        val assets = asset.readAssets()
        val faces = Face.values()
        for (face in faces) {
            val faceAsset =
                assets.find { it.name.matches(Regex(".*$face(\\.[^.]+)?$", RegexOption.IGNORE_CASE)) } ?: continue

            val image = faceAsset.readImage()
            println(image.getRGB(0, 0).toString(16))

            glCall {
                glTexImage2D(
                    face.id,
                    0,
                    GL_RGBA8,
                    image.width,
                    image.height,
                    0,
                    GL_RGBA,
                    GL_UNSIGNED_BYTE,
                    image.toByteBuffer()
//                    imgToBuffer(image)
                )
            }
        }


    }

    override fun updateWrapping() {
        super.updateWrapping()
        glCall { glTexParameteri(target, GL_TEXTURE_WRAP_R, wrapping.id) }
    }

    enum class Face(val id: Int) {
        RIGHT(GL13C.GL_TEXTURE_CUBE_MAP_POSITIVE_X),
        LEFT(GL13C.GL_TEXTURE_CUBE_MAP_NEGATIVE_X),
        TOP(GL13C.GL_TEXTURE_CUBE_MAP_POSITIVE_Y),
        BOTTOM(GL13C.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y),
        BACK(GL13C.GL_TEXTURE_CUBE_MAP_POSITIVE_Z),
        FRONT(GL13C.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z)
    }
}