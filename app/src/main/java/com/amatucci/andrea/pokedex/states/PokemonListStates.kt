package com.amatucci.andrea.pokedex.states

import com.amatucci.andrea.pokedex.model.Pokemon
import io.uniflow.core.flow.data.UIState

open class PokemonListStates : UIState() {
    object LoadingPokemonListState : PokemonListStates()
    object LoadingPokemonListErrorState : PokemonListStates()
    data class PokemonListState(val pokemonList : List<Pokemon>) : PokemonListStates()
}





