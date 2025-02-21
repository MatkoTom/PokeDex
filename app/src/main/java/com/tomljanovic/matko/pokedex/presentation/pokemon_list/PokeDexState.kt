package com.tomljanovic.matko.pokedex.presentation.pokemon_list

import com.tomljanovic.matko.pokedex.domain.model.Pokemon

data class PokeDexState(
    val pokemonList: List<Pokemon> = emptyList(),
    val pokemon: Pokemon? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)