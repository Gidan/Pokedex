package com.amatucci.andrea.pokedex

import android.util.Log
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.amatucci.andrea.pokedex.model.Pokemon
import com.amatucci.andrea.pokedex.model.PokemonList
import com.amatucci.andrea.pokedex.repository.PokemonRemoteMediator
import com.amatucci.andrea.pokedex.repository.PokemonRepository
import com.amatucci.andrea.pokedex.states.PokemonListStates
import io.uniflow.androidx.flow.AndroidDataFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokemonViewModel(private val pokemonRepository: PokemonRepository,
                       pokemonRemoteMediator: PokemonRemoteMediator) : AndroidDataFlow() {

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
    var selectedPokemonName : LiveData<String>
    var selectedPokemonId : LiveData<String>

    init {
        selectedPokemonName = Transformations.map(selectedPokemon){
            it?.name
        }
        selectedPokemonId = Transformations.map(selectedPokemon){
            "#${it?.id}"
        }
    }

    val fullPokemonList = Pager(
        config = PagingConfig(pageSize = 20),
        remoteMediator = pokemonRemoteMediator
    ) {
        pokemonRepository.pokemonDao.getPokemons()
    }

    suspend fun pokemon(id: Int) : Pokemon = pokemonRepository.pokemon(id)



}