package com.tomljanovic.matko.pokedex.domain.model

data class Pokemon(
    val id: Int,
    val name: String,
    val stats: Map<String, Int>,
    val types: List<String>,
    val sprite: String
)