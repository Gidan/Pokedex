package com.amatucci.andrea.pokedex.repository

import com.amatucci.andrea.pokedex.room.PokemonDAO

class PokemonRepository(
    val pokemonDao: PokemonDAO
) {

    /**
     * Retrieve pokemon from db.
     */
    suspend fun pokemon(id: Int) = pokemonDao.pokemonByID(id)

}