package com.amatucci.andrea.pokedex.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.amatucci.andrea.pokedex.PokemonDetailsActivity
import com.amatucci.andrea.pokedex.PokemonViewModel
import com.amatucci.andrea.pokedex.R
import com.amatucci.andrea.pokedex.adapters.OnItemClickListener
import com.amatucci.andrea.pokedex.adapters.PokemonListAdapter
import com.amatucci.andrea.pokedex.adapters.PokemonLoadStateAdapter
import com.amatucci.andrea.pokedex.databinding.FragmentListBinding
import com.amatucci.andrea.pokedex.model.Pokemon
import com.amatucci.andrea.pokedex.states.PokemonListStates
import com.amatucci.andrea.pokedex.util.PokemonDataUtil
import io.uniflow.androidx.flow.onStates
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
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

    @ExperimentalPagingApi
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

    @ExperimentalPagingApi
    private fun setup(){
        val adapter = PokemonListAdapter(object : OnItemClickListener{
            override fun onItemClicked(position: Int, commonView: View, pokemon: Pokemon?) {
                val intent = Intent(context, PokemonDetailsActivity::class.java)
                pokemon?.let {
                    val imgUrl = PokemonDataUtil.getArtwork(pokemon)
                    intent.putExtra("pokemonArtworkUrl", imgUrl)
                    intent.putExtra("id", pokemon.id)
                }

                startActivity(intent)
            }
        })
        binding.pokemonList.adapter = adapter

        onStates(pokemonViewModel) { state ->
            when (state) {
                is PokemonListStates.InitPokemonListState -> {
                    Log.d(logTag, "init pokemons")
                }
                is PokemonListStates.LoadingPokemonListState -> {
                    Log.d(logTag, "loading pokemons")
                    binding.loadingPokemonListProgress.visibility = View.VISIBLE
                }
                is PokemonListStates.LoadingPokemonListErrorState -> {
                    Log.e(logTag, "error state: ${state.exception.message}")
                    binding.loadingPokemonListProgress.visibility = View.GONE
                    Toast.makeText(context, getString(R.string.error_loading_list), Toast.LENGTH_SHORT).show()
                }
                is PokemonListStates.LoadedPokemonListState -> {
                    Log.d(logTag, "loaded")
                    binding.loadingPokemonListProgress.visibility = View.GONE
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi::class)
            pokemonViewModel.fullPokemonList.flow.collectLatest {
                adapter.submitData(it)
            }
        }

        binding.swipeRefresh.setOnRefreshListener { adapter.refresh() }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi::class)
            adapter.loadStateFlow.collectLatest { loadStates ->
                binding.swipeRefresh.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }

        adapter
            .withLoadStateHeaderAndFooter(
                header = PokemonLoadStateAdapter(adapter::retry),
                footer = PokemonLoadStateAdapter(adapter::retry)
            )
    }
}