package com.amatucci.andrea.pokedex.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.amatucci.andrea.pokedex.model.Pokemon
import com.amatucci.andrea.pokedex.network.PokemonService
import com.amatucci.andrea.pokedex.room.PokemonDAO

class PokemonRepository(
    private val pokemonService: PokemonService,
    val pokemonDao: PokemonDAO
) {

    suspend fun pokemonList() = pokemonService.pokemonList()

    suspend fun pokemon(id: Int) = pokemonService.pokemon(id)

    fun getPokemonList(): LiveData<List<Pokemon>> {
        return pokemonDao.getAllPokemons()
    }

    suspend fun refreshData() {
        try {
            for (i in 1..20) {
                val result = runCatching {
                    pokemon(i)
                }

                result.onSuccess {
                    Log.d("PokemonRepository", "pokemon call done $i")
                    pokemonDao.insertPokemon(it)
                }
                result.onFailure {
                    throw it
                }
            }

        } catch (e: Exception) {
            Log.e("PokemonRepository", "error ${e.message}")
        }
        Log.d("PokemonRepository", "refresh end")
    }


}