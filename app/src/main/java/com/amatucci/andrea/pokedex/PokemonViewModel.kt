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
        val list = runCatching { pokemonRepository.pokemonList() }
        list.onSuccess {
            emit(it)
        }
        list.onFailure {
            Log.e("PokemonViewModel", "pokemonList failure ${it.message}")
        }
    }

    private val _selectedPokemon = MutableLiveData<Pokemon?>(null)
    val selectedPokemon: LiveData<Pokemon?> get() = _selectedPokemon

    suspend fun requestPokemon(id: Int = 0) {
        val pokemon = pokemonRepository.pokemon(id)
        _selectedPokemon.postValue(pokemon)
    }

    private val _pokemonList = ArrayList<Pokemon>()

    init {
        action {
            if (_pokemonList.isEmpty()){
                setState(PokemonListStates.InitPokemonListState)
            }
        }
    }

    fun getPokemonList() = action {
        setState(PokemonListStates.LoadingPokemonListState)
        try {
            for (i in 1..20) {
                val pokemon = runCatching {
                    pokemonRepository.pokemon(i)
                }

                pokemon.onSuccess {
                    _pokemonList.add(it)
                }
                pokemon.onFailure {
                    throw it
                }
            }
            setState(PokemonListStates.PokemonListState(_pokemonList))
        } catch (e: Exception) {
            Log.e("PokemonViewModel", "error ${e.message}")
            _pokemonList.clear()
            setState(PokemonListStates.LoadingPokemonListErrorState(e))
        }

    }


}