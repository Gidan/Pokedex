package com.amatucci.andrea.pokedex.network

import com.amatucci.andrea.pokedex.model.Pokemon
import com.amatucci.andrea.pokedex.model.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {

    @GET("pokemon")
    suspend fun pokemonList(@Query("offset") offset: Int = 0, @Query("limit") limit: Int = 20) : PokemonList

    @GET("pokemon/{id}")
    suspend fun pokemon(@Path("id") id: Int) : Pokemon

}