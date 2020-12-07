package com.amatucci.andrea.pokedex.repository

import com.amatucci.andrea.pokedex.network.PokemonService

class PokemonRepository(private val pokemonService: PokemonService) {

  suspend fun pokemonList() = pokemonService.pokemonList()

  suspend fun pokemon(id: Int) = pokemonService.pokemon(id)


}