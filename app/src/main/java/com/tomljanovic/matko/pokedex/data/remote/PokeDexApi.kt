package com.tomljanovic.matko.pokedex.data.remote

import com.tomljanovic.matko.pokedex.data.remote.dto.PokemonResponse
import com.tomljanovic.matko.pokedex.util.Const
import retrofit2.http.GET
import retrofit2.http.Query

interface PokeDexApi {
    @GET(Const.Api.POKEMON)
    suspend fun getPokemon(
        @Query(Const.Api.LIMIT) limit: Int
    ): PokemonResponse

    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }
}