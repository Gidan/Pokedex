package com.amatucci.andrea.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.amatucci.andrea.pokedex.databinding.ActivityMainBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val pokemonViewModel: PokemonViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.model = pokemonViewModel
        binding.lifecycleOwner = this
        val view = binding.root
        setContentView(view)

        pokemonViewModel.pokemonList.observe(this, Observer {
            Log.d("MainActivity", it.toString())
        })
    }
}