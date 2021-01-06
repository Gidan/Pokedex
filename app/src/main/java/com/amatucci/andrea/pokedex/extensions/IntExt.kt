package com.amatucci.andrea.pokedex.extensions

import android.content.res.Resources

/**
 * Convert Pixel do Pixel Density points
 */
fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

/**
 * Convert Pixel Density points to Pixel
 */
fun Int.toPx(): Int  = (this * Resources.getSystem().displayMetrics.density).toInt()