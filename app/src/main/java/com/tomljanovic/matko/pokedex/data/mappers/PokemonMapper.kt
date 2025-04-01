package com.tomljanovic.matko.pokedex.data.mappers

import com.tomljanovic.matko.pokedex.data.local.PokemonEntity
import com.tomljanovic.matko.pokedex.data.remote.dto.PokemonResponse
import com.tomljanovic.matko.pokedex.domain.model.Pokemon

fun PokemonEntity.toPokemon(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        stats = stats,
        types = types,
        sprite = sprite,
        animatedSprite = animatedSprite ?: ""
    )
}

fun PokemonResponse.toPokemonEntity(): PokemonEntity {
    val types = types.map { it.type.name }
    val stats = stats.associate { it.stat.name to it.baseStat }

    return PokemonEntity(
        id = id,
        name = name,
        stats = stats,
        types = types,
        sprite = sprites.other.officialArtwork.frontDefault,
        animatedSprite = sprites.other.showdown.frontDefault
    )
}