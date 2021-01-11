package com.amatucci.andrea.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.amatucci.andrea.pokedex.model.Pokemon
import com.amatucci.andrea.pokedex.repository.PokemonRemoteMediator
import com.amatucci.andrea.pokedex.repository.PokemonRepository
import io.uniflow.androidx.flow.AndroidDataFlow

class PokemonViewModel(private val pokemonRepository: PokemonRepository,
                       pokemonRemoteMediator: PokemonRemoteMediator) : AndroidDataFlow() {

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