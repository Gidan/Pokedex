package com.amatucci.andrea.pokedex.util

import com.amatucci.andrea.pokedex.model.Pokemon

class PokemonDataUtil {

    companion object {
        fun getArtwork(pokemon: Pokemon) : String? {
            return pokemon.sprites.otherSprites.artwork.front_default
        }
    }


}