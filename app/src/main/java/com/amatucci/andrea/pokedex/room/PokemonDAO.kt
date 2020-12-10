package com.amatucci.andrea.pokedex.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import androidx.room.Transaction
import com.amatucci.andrea.pokedex.model.Pokemon
import com.amatucci.andrea.pokedex.model.Stat
import com.amatucci.andrea.pokedex.model.Type


@Dao
abstract class PokemonDAO {

    @Transaction
    @Query("SELECT * FROM Pokemon")
    abstract fun getPokemonWithStatAndType(): LiveData<List<Pokemon>>

    @Insert(onConflict = IGNORE)
    abstract fun insertPokemon(pokemon : Pokemon)

    @Insert(onConflict = IGNORE)
    abstract fun insertStat(stat : Stat)

    @Insert(onConflict = IGNORE)
    abstract fun insertType(type : Type)

}