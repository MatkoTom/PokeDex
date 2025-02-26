package com.tomljanovic.matko.pokedex.data.remote.dto

data class PokemonListResponse(
    val results: List<ResponseResults>
)

data class ResponseResults(
    val name: String
)