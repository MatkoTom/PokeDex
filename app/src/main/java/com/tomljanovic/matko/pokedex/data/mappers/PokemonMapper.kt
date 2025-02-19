package com.tomljanovic.matko.pokedex.data.mappers

import com.tomljanovic.matko.pokedex.data.local.PokemonEntity
import com.tomljanovic.matko.pokedex.data.remote.dto.ResponseResults
import com.tomljanovic.matko.pokedex.domain.model.Pokemon

fun PokemonEntity.toPokemon(): Pokemon {
    return Pokemon(
        name = name
    )
}

// TODO change this to mapp to local db entity
fun ResponseResults.toPokemonEntity(): PokemonEntity {
    return PokemonEntity(
//        id = id,
        name = name,
//        order = order,
//        weight = weight,
//        sprites = sprites,
//        stats = stats,
//        types = types
    )
}