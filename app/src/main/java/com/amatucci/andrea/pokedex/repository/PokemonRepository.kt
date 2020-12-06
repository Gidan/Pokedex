package com.amatucci.andrea.pokedex.repository

import com.amatucci.andrea.pokedex.network.PokemonService

class PokemonRepository(val pokemonService: PokemonService) {

  suspend fun pokemonList() = pokemonService.pokemonList()


}