package com.tomljanovic.matko.pokedex.domain.repository

import com.tomljanovic.matko.pokedex.domain.model.Pokemon
import com.tomljanovic.matko.pokedex.util.Resource
import kotlinx.coroutines.flow.Flow

interface PokeDexRepository {
    fun getPokemonList(limit: Int, isFromRemote: Boolean): Flow<Resource<List<Pokemon>>>
    fun getNextPage(limit: Int): Flow<Resource<List<Pokemon>>>
}