package com.amatucci.andrea.pokedex.diModules

import com.amatucci.andrea.pokedex.network.PokemonService
import com.amatucci.andrea.pokedex.repository.PokemonRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    single { provideRetrofit() }
    factory { providePokemonService(get()) }
    single { PokemonRepository(get()) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
}

fun providePokemonService(retrofit: Retrofit) : PokemonService {
    return retrofit.create(PokemonService::class.java)
}
