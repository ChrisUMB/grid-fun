package me.chrisumb.grids

import me.chrisumb.grids.asset.Asset
import me.chrisumb.grids.render.texture.Texture2D
import me.chrisumb.grids.things.Cube
import me.chrisumb.grids.things.SkyBox
import org.joml.Vector3f

fun main() {
    Engine.start(1280, 720, "Test") {
        drawables.add(SkyBox(Asset["textures/skyboxes/vaporwave"]))
        val cube = Cube(Vector3f(0f, 0f, -3f))
        cube.texture = Texture2D(Asset["textures/skyboxes/vaporwave/back.png"])
        drawables.add(cube)
    }
}