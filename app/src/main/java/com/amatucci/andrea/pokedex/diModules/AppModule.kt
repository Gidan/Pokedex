package com.amatucci.andrea.pokedex.diModules

import com.amatucci.andrea.pokedex.PokemonDetailsViewModel
import com.amatucci.andrea.pokedex.PokemonViewModel
import org.koin.android.viewmodel.dsl.viewModel

import org.koin.dsl.module


val appModule = module {
    viewModel { PokemonViewModel(get(), get()) }
    viewModel { PokemonDetailsViewModel(get()) }
}