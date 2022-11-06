package com.amatucci.andrea.pokedex.util

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.amatucci.andrea.pokedex.customviews.Type
import com.amatucci.andrea.pokedex.model.Pokemon

fun blendedColor(pokemon : Pokemon, context : Context) : Int? {
    val map = pokemon.types.map { it.type.name }.map { ContextCompat.getColor(
        context, Type.valueOf(
            it
        ).colorRes
    ) }
    return blendColors(*map.toIntArray())
}

/**
 * Blend between a list of colors to get the average color
 */
fun blendColors(vararg color : Int) : Int? {
    var retColor : Int? = null

    color.forEach {
        retColor = retColor?.let { r -> ColorUtils.blendARGB(r, it, 0.5f) } ?: it
    }

    return retColor
}

