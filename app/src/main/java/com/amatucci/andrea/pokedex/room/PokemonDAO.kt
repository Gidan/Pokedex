package com.amatucci.andrea.pokedex.room

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.amatucci.andrea.pokedex.model.Pokemon

@Dao
interface PokemonDAO {

    @Query("SELECT * FROM Pokemon")
    fun getAllPokemons(): LiveData<List<Pokemon>>

    @Insert(onConflict = IGNORE)
    fun insertPokemon(pokemon : Pokemon)

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(pokemons: List<Pokemon>)

    @Query("SELECT * FROM Pokemon")
    fun getPokemons(): PagingSource<Int, Pokemon>

    @Query("DELETE FROM Pokemon")
    suspend fun clearAll()

    @Query("SELECT * FROM Pokemon WHERE id = :id")
    suspend fun pokemonByID(id : Int) : Pokemon

    @Query("SELECT * FROM Pokemon WHERE name = :name")
    suspend fun pokemonByName(name : String) : Pokemon


}