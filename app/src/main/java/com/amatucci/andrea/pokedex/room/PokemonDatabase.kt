package com.amatucci.andrea.pokedex.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.amatucci.andrea.pokedex.model.Pokemon
import com.amatucci.andrea.pokedex.model.Stat
import com.amatucci.andrea.pokedex.model.Type
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

@Database(entities = [Pokemon::class, Stat::class, Type::class], version = 1)
@TypeConverters(Converters::class)
abstract class PokemonDatabase : RoomDatabase(){
    abstract fun pokemonDao(): PokemonDAO
}

class Converters {
    @TypeConverter
    fun fromJsonToStats(value: String): List<Stat>? {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, Stat::class.java)
        val adapter = moshi.adapter<List<Stat>>(type)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromStatsToJson(value: List<Stat>): String? {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, Stat::class.java)
        val adapter = moshi.adapter<List<Stat>>(type)
        return adapter.toJson(value)
    }

    @TypeConverter
    fun fromJsonToTypes(value: String): List<Type>? {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, Type::class.java)
        val adapter = moshi.adapter<List<Type>>(type)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromTypesToJson(value: List<Type>): String? {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, Type::class.java)
        val adapter = moshi.adapter<List<Type>>(type)
        return adapter.toJson(value)
    }
}

