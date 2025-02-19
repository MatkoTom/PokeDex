package com.tomljanovic.matko.pokedex.presentation

import com.tomljanovic.matko.pokedex.domain.model.Pokemon

data class PokeDexState(
    val pokemon: List<Pokemon> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)