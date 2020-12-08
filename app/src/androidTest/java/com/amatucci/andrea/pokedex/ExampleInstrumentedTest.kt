package com.amatucci.andrea.pokedex

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.amatucci.andrea.pokedex.util.blendColors
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.amatucci.andrea.pokedex", appContext.packageName)
    }


    @Test
    fun blendColorsTest(){
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val grass = ContextCompat.getColor(appContext, R.color.type_grass)
        val poison = ContextCompat.getColor(appContext, R.color.type_poison)
        val blendARGB = blendColors(grass, poison)
        assertNotNull(blendARGB)

        val red = Color.red(blendARGB!!)
        val green = Color.green(blendARGB)
        val blue = Color.blue(blendARGB)
        assertEquals(red, 142)
        assertEquals(green, 130)
        assertEquals(blue, 118)
    }
}