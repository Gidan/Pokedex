package com.amatucci.andrea.pokedex.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amatucci.andrea.pokedex.PokemonViewModel
import com.amatucci.andrea.pokedex.databinding.FragmentListBinding
import com.amatucci.andrea.pokedex.states.PokemonListStates
import io.uniflow.androidx.flow.onStates
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {

    private val pokemonViewModel: PokemonViewModel by viewModel()
    private lateinit var binding : FragmentListBinding

    private val logTag = ListFragment::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(layoutInflater)
        binding.model = pokemonViewModel
        binding.lifecycleOwner = this

        setup()

        return binding.root
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d("ListFragment", "onConfigurationChanged")
    }

    private fun setup(){
        //pokemonViewModel.pokemonList.observe(this, Observer {
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
                is PokemonListStates.InitPokemonListState -> {
                    pokemonViewModel.getPokemonList()
                }
                is PokemonListStates.LoadingPokemonListState -> {
                    Log.d(logTag, "loading pokemons")
                    binding.loadingPokemonListProgress.visibility = View.VISIBLE
                }
                is PokemonListStates.PokemonListState -> {
                    Log.d(logTag, state.pokemonList.toString())
                    binding.loadingPokemonListProgress.visibility = View.GONE
                }
                is PokemonListStates.LoadingPokemonListErrorState -> {
                    Log.e(logTag, "error state: ${state.exception.message}")
                    binding.loadingPokemonListProgress.visibility = View.GONE
                }
            }
        }


    }

    override fun onStart() {
        super.onStart()

    }


}