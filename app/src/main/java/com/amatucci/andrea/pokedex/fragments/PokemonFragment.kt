package com.amatucci.andrea.pokedex.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amatucci.andrea.pokedex.PokemonViewModel
import com.amatucci.andrea.pokedex.databinding.FragmentPokemonBinding
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 * Use the [PokemonFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PokemonFragment : Fragment() {

    private val pokemonViewModel: PokemonViewModel by viewModel()
    private lateinit var binding : FragmentPokemonBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPokemonBinding.inflate(layoutInflater)
        binding.model = pokemonViewModel
        binding.lifecycleOwner = this
        return binding.root
    }


}