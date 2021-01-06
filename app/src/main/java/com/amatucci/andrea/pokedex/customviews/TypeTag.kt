package com.amatucci.andrea.pokedex.customviews

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.amatucci.andrea.pokedex.R
import com.amatucci.andrea.pokedex.extensions.toPx
import java.lang.IllegalArgumentException
import java.util.*

enum class Type(val colorRes: Int) {
    normal(R.color.type_normal),
    fire(R.color.type_fire),
    water(R.color.type_water),
    electric(R.color.type_electric),
    grass(R.color.type_grass),
    ice(R.color.type_ice),
    fighting(R.color.type_fighting),
    poison(R.color.type_poison),
    ground(R.color.type_ground),
    flying(R.color.type_flying),
    psychic(R.color.type_psychic),
    bug(R.color.type_bug),
    rock(R.color.type_rock),
    ghost(R.color.type_ghost),
    dragon(R.color.type_dragon),
    dark(R.color.type_dark),
    steel(R.color.type_steel),
    fairy(R.color.type_fairy),

}

class TypeTag : FrameLayout {

    private lateinit var txtTypeName : TextView

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        txtTypeName = TextView(context)
        txtTypeName.gravity = Gravity.CENTER
        txtTypeName.setTextColor(ContextCompat.getColor(context, android.R.color.white))
        addView(txtTypeName)
        background = ContextCompat.getDrawable(context, R.drawable.type_tag_background)
        alpha = 0f

        setPadding(15.toPx(), 2.toPx(), 15.toPx(), 2.toPx())
        val params = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        params.rightMargin = 10.toPx()
        layoutParams = params
    }

    fun setType(typeName: String?) {
        visibility = if (typeName != null) View.VISIBLE else View.GONE
        typeName?.let{
            txtTypeName.text = typeName.toUpperCase(Locale.getDefault())
            val colorRes: Int =
                try {
                    Type.valueOf(typeName).colorRes
                } catch (e: IllegalArgumentException) {
                    R.color.type_normal
                }
            backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(context, colorRes))
            alpha = 1f
        }
    }
}