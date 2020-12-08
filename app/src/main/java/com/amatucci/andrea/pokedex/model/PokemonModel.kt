package com.amatucci.andrea.pokedex.model

import com.squareup.moshi.Json

data class PokemonList(
    val count: Int,
    val next: String,
    val previous: Int?,
    val results: List<PokemonLink>
)

data class PokemonLink(
    val name: String,
    val url: String
)

data class Pokemon(
    val id: Int,
    val name: String,
    val weight: Int,
    val height: Int,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<Type>
)

data class Sprites(
    val front_default: String,
    @field:Json(name = "other") val otherSprites: OtherSprites
)

data class OtherSprites(
    @field:Json(name = "official-artwork") val artwork: OfficialArtwork
)

data class OfficialArtwork(
    val front_default: String?
)

data class Stat(
    val base_stat: Int,
    val effort: Int,
    val stat: StatLink
)

data class StatLink(
    val name: String,
    val url: String
)

data class Type(
    val slot: Int,
    val type: TypeDetails
)

data class TypeDetails(
    val name: String,
    val url: String
)


