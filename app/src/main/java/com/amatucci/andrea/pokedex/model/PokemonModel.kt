package com.amatucci.andrea.pokedex.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.squareup.moshi.Json

data class PokemonList(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonLink>
)

//@Entity
data class PokemonLink(
//    @PrimaryKey
    val name: String,
    val url: String
)

//data class PokemonWithUrl(
//    @Embedded(prefix = "link_") val pokemonLink: PokemonLink,
//    @Relation(
//        parentColumn = "name",
//        entityColumn = "name"
//    )
//    val pokemon: Pokemon
//)

@Entity
data class Pokemon(
    @PrimaryKey val name: String,
    val id: Int,
    val weight: Int,
    val height: Int,
    @Embedded val sprites: Sprites,
    var stats : List<Stat>,
    var types : List<Type>
)

data class Sprites(
    val front_default: String,
    @Embedded  @field:Json(name = "other") val otherSprites: OtherSprites
)

data class OtherSprites(
    @Embedded(prefix = "official_") @field:Json(name = "official-artwork") val artwork: OfficialArtwork
)

data class OfficialArtwork(
    val front_default: String?
)

@Entity
data class Stat(
    @PrimaryKey(autoGenerate = true) val dbId: Int,
    val id: Int,
    val base_stat: Int,
    val effort: Int,
    @Embedded val stat: StatLink
)

data class StatLink(
    val name: String,
    val url: String
)

@Entity
data class Type(
    @PrimaryKey(autoGenerate = true) val dbId: Int,
    val id: Int,
    val slot: Int,
    @Embedded val type: TypeDetails
)

data class TypeDetails(
    val name: String,
    val url: String
)






