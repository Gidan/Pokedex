package com.amatucci.andrea.pokedex.diModules

import com.amatucci.andrea.pokedex.network.PokemonService
import com.amatucci.andrea.pokedex.repository.PokemonRemoteMediator
import com.amatucci.andrea.pokedex.room.PokemonDatabase
import org.koin.dsl.module

val pagingModule = module {
    single { provideRemoteMediator(get(), get()) }
}

fun provideRemoteMediator(pokemonDatabase: PokemonDatabase, pokemonService: PokemonService) : PokemonRemoteMediator{
    return PokemonRemoteMediator(pokemonDatabase, pokemonService)
}

