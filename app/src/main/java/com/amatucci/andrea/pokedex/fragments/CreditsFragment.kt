package com.amatucci.andrea.pokedex.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amatucci.andrea.pokedex.PokemonViewModel
import com.amatucci.andrea.pokedex.databinding.FragmentCreditsBinding
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 * Use the [CreditsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreditsFragment : Fragment() {

    private val pokemonViewModel: PokemonViewModel by viewModel()
    private lateinit var binding : FragmentCreditsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreditsBinding.inflate(layoutInflater)
        binding.model = pokemonViewModel
        binding.lifecycleOwner = this
        return binding.root
    }


}