package com.amatucci.andrea.pokedex.network

import com.amatucci.andrea.pokedex.model.PokemonList
import retrofit2.http.GET

interface PokemonService {

    @GET("pokemon")
    suspend fun pokemonList() : PokemonList

}