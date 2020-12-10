package com.amatucci.andrea.pokedex.diModules

import android.app.Application
import androidx.room.Room
import com.amatucci.andrea.pokedex.room.PokemonDAO
import com.amatucci.andrea.pokedex.room.PokemonDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dbModule = module {
    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}

fun provideDatabase(application : Application) : PokemonDatabase{
    return Room.databaseBuilder(
        application,
        PokemonDatabase::class.java, "pokemon-db"
    ).build()
}

fun provideDao(db : PokemonDatabase) : PokemonDAO{
    return db.pokemonDao()
}
