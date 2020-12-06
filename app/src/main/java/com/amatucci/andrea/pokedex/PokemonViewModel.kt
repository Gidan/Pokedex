package com.amatucci.andrea.pokedex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.amatucci.andrea.pokedex.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers

class PokemonViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {

    val pokemonList = liveData(Dispatchers.IO) {
        emit(pokemonRepository.pokemonList())
    }

}