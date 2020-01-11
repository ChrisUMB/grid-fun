package me.chrisumb.grids.util

import org.joml.Vector2f
import org.joml.Vector3f
import org.joml.Vector4f

fun Vector2f.toFloatArray(): FloatArray {
    return floatArrayOf(x(), y())
}

fun Vector3f.toFloatArray(): FloatArray {
    return floatArrayOf(x(), y(), z())
}

fun Vector4f.toFloatArray(): FloatArray {
    return floatArrayOf(x(), y(), z(), w())
}