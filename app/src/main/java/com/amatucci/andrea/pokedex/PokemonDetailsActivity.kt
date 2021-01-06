package com.amatucci.andrea.pokedex

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

        pokemonDetailsViewModel.type1.observe(this, Observer {
            binding.type1.setType(it)
        })
        pokemonDetailsViewModel.type2.observe(this, Observer {
            binding.type2.setType(it)
        })

        pokemonDetailsViewModel.stats.observe(this, Observer { list ->
            val chartData = list.sortedByDescending { it.first }
            val lblValData = list.sortedBy { it.first }

            binding.statChart.animate(chartData)
            binding.statsValues.removeAllViews()
            lblValData.forEach { (_, value) ->
                binding.statsValues.addView(createStatText(value))
            }
        })

        pokemonDetailsViewModel.setIntent(intent)

    }

    private fun createStatText(statValue : Float) : View {
        return TextView(baseContext).apply {
            text = statValue.toInt().toString()
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
            setTextColor(ContextCompat.getColor(baseContext, R.color.white))
            gravity = Gravity.CENTER_VERTICAL
        }

    }
}