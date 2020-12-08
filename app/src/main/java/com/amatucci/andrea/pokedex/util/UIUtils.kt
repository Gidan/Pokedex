package com.amatucci.andrea.pokedex.util

import android.content.res.Resources.getSystem
import androidx.core.graphics.ColorUtils

fun Int.toDp(): Int = (this / getSystem().displayMetrics.density).toInt()

fun Int.toPx(): Int  = (this * getSystem().displayMetrics.density).toInt()

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