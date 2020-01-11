package me.chrisumb.grids

import me.chrisumb.grids.asset.Asset
import me.chrisumb.grids.render.GLBuffer
import me.chrisumb.grids.things.WavefrontModel
import me.chrisumb.grids.util.toFloatArray
import org.joml.Vector2f
import org.joml.Vector3f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL15C.GL_ELEMENT_ARRAY_BUFFER
import java.nio.FloatBuffer

object WavefrontLoader {

    fun parse(asset: Asset): WavefrontModel {

        val positions = ArrayList<Vector3f>()
        val texCoords = ArrayList<Vector2f>()
        val normals = ArrayList<Vector3f>()
        val indices = ArrayList<VertexIndex>()

        val lines = asset.readText().lines()
        for (line in lines) {
            val split = line.split(' ')

            when (split[0]) {
                "v" -> positions.add(Vector3f(splitToFloats(split, 3)))
                "vt" -> texCoords.add(Vector2f(splitToFloats(split, 2)))
                "vn" -> normals.add(Vector3f(splitToFloats(split, 3)))
                "f" -> {

                    for (i in 1..3) {
                        val fSplit = split[i].split("/")
                        val vIndex = fSplit[0].toInt() - 1
                        val vTexCoordIndex = fSplit[1].toInt() - 1
                        val vNormal = fSplit[2].toInt() - 1
                        indices.add(VertexIndex(vIndex, vTexCoordIndex, vNormal))
                    }
                }
            }
        }

        val vertices = ArrayList<WavefrontModel.Vertex>()
        val iboData = ArrayList<Int>()

        for (index in indices) {
            val pos = positions[index.position]
            val tCoord = texCoords[index.texCoord]
            val normal = normals[index.normal]
            val vertex = WavefrontModel.Vertex(pos, tCoord, normal)

            val indexOf = vertices.indexOf(vertex)
            if (indexOf == -1) {
                iboData.add(vertices.size)
                vertices.add(vertex)
            } else {
                iboData.add(indexOf)
            }
        }

        val ibo = GLBuffer(GL_ELEMENT_ARRAY_BUFFER)
        ibo.data(iboData.toIntArray())

        val vbo = GLBuffer()
        val data = vertices.flatMap { vertex ->
            listOf(
                vertex.position.toFloatArray(),
                vertex.texCoord.toFloatArray(),
                vertex.normal.toFloatArray()
            ).flatMap { it.toList() }
        }.toFloatArray()

        vbo.data(data)

        println(data.contentToString())
        println(iboData)
        println(vertices.size)

        return WavefrontModel(ibo, vbo, iboData.size)
    }

    private fun splitToFloats(split: List<String>, count: Int): FloatBuffer {
        val buffer = BufferUtils.createFloatBuffer(count)
        for (i in 1..count) {
            buffer.put(split[i].toFloat())
        }

        buffer.flip()
        return buffer
    }

    data class VertexIndex(val position: Int, val texCoord: Int, val normal: Int)
}