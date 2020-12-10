package com.amatucci.andrea.pokedex.fragments

import android.app.ActivityOptions
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.Observer
import com.amatucci.andrea.pokedex.PokemonDetailsActivity
import com.amatucci.andrea.pokedex.PokemonViewModel
import com.amatucci.andrea.pokedex.adapters.OnItemClickListener
import com.amatucci.andrea.pokedex.adapters.PokemonListAdapter
import com.amatucci.andrea.pokedex.databinding.FragmentListBinding
import com.amatucci.andrea.pokedex.model.Stat
import com.amatucci.andrea.pokedex.model.Type
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

    private fun setup(){
        val adapter = PokemonListAdapter(object : OnItemClickListener{
            override fun onItemClicked(position: Int, commonView: View) {
                val intent = Intent(context, PokemonDetailsActivity::class.java)
                val drawableBitmap = (commonView as ImageView).drawable.toBitmap()
                intent.putExtra("pokemonArtwork", drawableBitmap)
//                val options = ActivityOptions
//                    .makeSceneTransitionAnimation(activity, commonView, "pokemonArtwork")
//                startActivity(intent, options.toBundle())
                startActivity(intent)
            }
        })
        binding.pokemonList.adapter = adapter

        onStates(pokemonViewModel) { state ->
            when (state) {
                is PokemonListStates.InitPokemonListState -> {
//                    pokemonViewModel.getPokemonList()
                    pokemonViewModel.getFullPokemonList()
                }
                is PokemonListStates.LoadingPokemonListState -> {
                    Log.d(logTag, "loading pokemons")
                    binding.loadingPokemonListProgress.visibility = View.VISIBLE
                }
//                is PokemonListStates.PokemonListState -> {
//                    Log.d(logTag, state.pokemonList.toString())
//                    binding.loadingPokemonListProgress.visibility = View.GONE
//                    adapter.submitList(state.pokemonList)
//                }
                is PokemonListStates.LoadingPokemonListErrorState -> {
                    Log.e(logTag, "error state: ${state.exception.message}")
                    binding.loadingPokemonListProgress.visibility = View.GONE
                }
                is PokemonListStates.LoadedPokemonListState -> {
                    Log.d(logTag, "loaded")
                    binding.loadingPokemonListProgress.visibility = View.GONE
                }
            }
        }

        pokemonViewModel.fullPokemonList.observe(viewLifecycleOwner, Observer { list ->
//            val pokemonList = list.map {
//                it.pokemon.stats = it.stats as ArrayList<Stat>
//                it.pokemon.types = it.types as ArrayList<Type>
//                it.pokemon
//            }.toList()
            adapter.submitList(list)
        })



    }



}