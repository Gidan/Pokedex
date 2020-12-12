package com.amatucci.andrea.pokedex.room

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.amatucci.andrea.pokedex.model.Pokemon
import com.amatucci.andrea.pokedex.model.Stat
import com.amatucci.andrea.pokedex.model.Type


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


}