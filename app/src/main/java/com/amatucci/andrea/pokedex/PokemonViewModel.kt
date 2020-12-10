package com.amatucci.andrea.pokedex

import android.util.Log
import androidx.lifecycle.*
import com.amatucci.andrea.pokedex.model.Pokemon
import com.amatucci.andrea.pokedex.model.PokemonList
import com.amatucci.andrea.pokedex.repository.PokemonRepository
import com.amatucci.andrea.pokedex.states.PokemonListStates
import io.uniflow.androidx.flow.AndroidDataFlow
import kotlinx.coroutines.Dispatchers

class PokemonViewModel(private val pokemonRepository: PokemonRepository) : AndroidDataFlow() {

    val pokemonList = liveData<PokemonList>(Dispatchers.IO) {
        val result = runCatching { pokemonRepository.pokemonList() }
        result.onSuccess {
            emit(it)
        }
        result.onFailure {
            Log.e("PokemonViewModel", "pokemonList failure ${it.message}")
        }
    }

    private val _selectedPokemon = MutableLiveData<Pokemon?>(null)
    val selectedPokemon: LiveData<Pokemon?> get() = _selectedPokemon

    suspend fun requestPokemon(id: Int = 0) {
        val pokemon = pokemonRepository.pokemon(id)
        _selectedPokemon.postValue(pokemon)
    }

    init {
        action {
            setState(PokemonListStates.InitPokemonListState)
        }
    }

    val fullPokemonList = pokemonRepository.getPokemonList()


    fun getFullPokemonList() = action (
        onAction = {
            setState(PokemonListStates.LoadingPokemonListState)
            pokemonRepository.refreshData()
            setState(PokemonListStates.LoadedPokemonListState)
        },
        onError = {error, _ ->
            Log.e("PokemonViewModel", "onError ${error.message}")
        }
    )

}