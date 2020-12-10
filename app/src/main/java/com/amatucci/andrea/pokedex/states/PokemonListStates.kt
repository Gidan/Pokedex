package com.amatucci.andrea.pokedex.states

import com.amatucci.andrea.pokedex.model.Pokemon
import io.uniflow.core.flow.data.UIState
import java.lang.Exception

open class PokemonListStates : UIState() {
    object InitPokemonListState : PokemonListStates()
    object LoadingPokemonListState : PokemonListStates()
    object LoadedPokemonListState : PokemonListStates()
    data class LoadingPokemonListErrorState(val exception: Exception) : PokemonListStates()
    data class PokemonListState(val pokemonList : List<Pokemon>) : PokemonListStates()
}





