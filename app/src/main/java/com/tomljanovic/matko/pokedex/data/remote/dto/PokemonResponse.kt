package com.tomljanovic.matko.pokedex.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    val results: List<ResponseResults>
)

data class ResponseResults(
//    val id: Int,
    val name: String,
//    val order: Int,
//    val weight: Int,
//    val sprites: List<String>,
//    val stats: List<Stats>,
//    val types: List<Types>,
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