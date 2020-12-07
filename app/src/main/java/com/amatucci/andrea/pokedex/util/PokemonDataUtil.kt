package com.amatucci.andrea.pokedex.util

import com.amatucci.andrea.pokedex.model.Pokemon

class PokemonDataUtil(private val pokemon: Pokemon) {

    fun getArtwork(): String? {
        return pokemon.sprites.otherSprites.artwork.front_default
    }


}