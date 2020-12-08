package com.amatucci.andrea.pokedex

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.ChangeImageTransform
import android.transition.Explode
import android.transition.Fade
import android.transition.Slide
import android.view.Window
import com.amatucci.andrea.pokedex.databinding.ActivityPokemonDetailsBinding

class PokemonDetailsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPokemonDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPokemonDetailsBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        val view = binding.root
        setContentView(view)

//        with(window){
//            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
//            enterTransition = Slide()
//            exitTransition = Slide()
//            sharedElementEnterTransition = ChangeImageTransform()
//            sharedElementExitTransition = ChangeImageTransform()
//        }

        val bitmap = intent.extras?.get("pokemonArtwork") as Bitmap
        val bitmapDrawable = BitmapDrawable(resources, bitmap)
        binding.ivPokemonArtwork.setImageBitmap(bitmapDrawable.bitmap)

    }
}