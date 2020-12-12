package com.amatucci.andrea.pokedex.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.amatucci.andrea.pokedex.PokemonDetailsActivity
import com.amatucci.andrea.pokedex.PokemonViewModel
import com.amatucci.andrea.pokedex.adapters.OnItemClickListener
import com.amatucci.andrea.pokedex.adapters.PokemonListAdapter
import com.amatucci.andrea.pokedex.adapters.PokemonLoadStateAdapter
import com.amatucci.andrea.pokedex.databinding.FragmentListBinding
import com.amatucci.andrea.pokedex.states.PokemonListStates
import io.uniflow.androidx.flow.onStates
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
//                    pokemonViewModel.getFullPokemonList()
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

//        pokemonViewModel.fullPokemonList.observe(viewLifecycleOwner, Observer { list ->
//            adapter.submitList(list)
//        })



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

//        lifecycleScope.launch {
//            adapter.loadStateFlow.collectLatest { loadStates ->
//                binding.loadingPokemonListProgress.isVisible = loadStates.refresh is LoadState.Loading
//                //retry.isVisible = loadStates.refresh !is LoadState.Loading
//                //errorMsg.isVisible = loadStates.refresh is LoadState.Error
//            }
//        }


    }



}