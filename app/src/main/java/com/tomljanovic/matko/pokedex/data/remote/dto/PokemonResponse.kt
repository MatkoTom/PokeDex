package com.tomljanovic.matko.pokedex.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    val id: Int,
    val name: String,
    val stats: List<Stats>,
    val types: List<Types>,
    val sprites: Sprites
)

data class Stats(
    @SerializedName("base_stat")
    val baseStat: Int,
    val stat: Stat
)

data class Types(
    val type: Type
)

data class Stat(
    val name: String
)

data class Type(
    val name: String
)

data class Sprites(
    val other: OtherSprites
)

data class OtherSprites(
    @SerializedName("official-artwork")
    val officialArtwork: Artwork,
    val showdown: Artwork,
)

data class Artwork(
    @SerializedName("front_default")
    val frontDefault: String
)