package com.amatucci.andrea.pokedex.util

import androidx.core.graphics.ColorUtils

/**
 * Blend between a list of colors to get the average color
 */
fun blendColors(vararg color : Int) : Int? {
    var retColor : Int? = null

    color.forEach {
        retColor = if (retColor == null){
            it
        } else {
            ColorUtils.blendARGB(retColor!!, it, 0.5f)
        }
    }

    return retColor
}