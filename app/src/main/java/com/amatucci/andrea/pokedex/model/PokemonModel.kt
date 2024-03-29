package com.amatucci.andrea.pokedex.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class PokemonList(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonLink>
)

data class PokemonLink(
    val name: String,
    val url: String
)

@Entity
data class Pokemon(
    @PrimaryKey val name: String,
    val id: Int,
    val weight: Int,
    val height: Int,
    @Embedded val sprites: Sprites,
    var stats : List<Stat>,
    var types : List<Type>
){
    fun typesCount() : Int {
        return types.size
    }
 }

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







