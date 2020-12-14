package com.amatucci.andrea.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.ObservableList
import androidx.lifecycle.Observer
import com.amatucci.andrea.pokedex.databinding.ActivityPokemonDetailsBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.koin.android.viewmodel.ext.android.viewModel

class PokemonDetailsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPokemonDetailsBinding

    private val pokemonDetailsViewModel: PokemonDetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPokemonDetailsBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.model = pokemonDetailsViewModel
        val view = binding.root
        setContentView(view)

//        with(window){
//            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
//            enterTransition = Slide()
//            exitTransition = Slide()
//            sharedElementEnterTransition = ChangeImageTransform()
//            sharedElementExitTransition = ChangeImageTransform()
//        }

//        val bitmap = intent.extras?.get("pokemonArtwork") as Bitmap
//        val bitmapDrawable = BitmapDrawable(resources, bitmap)
//        binding.ivPokemonArtwork.setImageBitmap(bitmapDrawable.bitmap)

        val imageUrl = intent.extras?.get("pokemonArtworkUrl") as String
        imageUrl.let {
            Glide.with(baseContext)
                .load(it)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_pokeball)
                .into(binding.ivPokemonArtwork)
        }

        pokemonDetailsViewModel.setIntent(intent)

        pokemonDetailsViewModel.type1.observe(this, Observer {
            binding.type1.setType(it)
        })
        pokemonDetailsViewModel.type2.observe(this, Observer {
            binding.type2.setType(it)
        })

    }
}