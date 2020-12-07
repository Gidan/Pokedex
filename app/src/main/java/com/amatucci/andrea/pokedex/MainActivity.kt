package com.amatucci.andrea.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.amatucci.andrea.pokedex.databinding.ActivityMainBinding
import com.amatucci.andrea.pokedex.states.PokemonListStates
import io.uniflow.androidx.flow.onStates
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

//        pokemonViewModel.pokemonList.observe(this, Observer {
//            Log.d("MainActivity", it.toString())
//        })
//
//        pokemonViewModel.selectedPokemon.observe(this, Observer {
//            Log.d("MainActivity", it?.toString() ?: "null pokemon")
//        })
//
//        pokemonViewModel.detailedPokemonList.observe(this, Observer {
//            Log.d("MainActivity", it?.toString() ?: "null detailed list")
//        })

        onStates(pokemonViewModel) { state ->
            when (state) {
                is PokemonListStates.LoadingPokemonListState -> {
                    Log.d("MainActivity", "loading pokemons")
                    binding.loadingPokemonListProgress.visibility = View.VISIBLE
                }
                is PokemonListStates.PokemonListState -> {
                    Log.d("MainActivity", state.pokemonList.toString())
                    binding.loadingPokemonListProgress.visibility = View.GONE
                }
                is PokemonListStates.LoadingPokemonListErrorState -> {
                    Log.e("MainActivity", "error state: ${state.exception.message}")
                    binding.loadingPokemonListProgress.visibility = View.GONE
                }
            }
        }


        pokemonViewModel.getPokemonList()



    }
}