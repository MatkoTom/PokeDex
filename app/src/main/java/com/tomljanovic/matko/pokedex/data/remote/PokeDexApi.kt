package com.tomljanovic.matko.pokedex.data.remote

import com.tomljanovic.matko.pokedex.data.remote.dto.PokemonListResponse
import com.tomljanovic.matko.pokedex.data.remote.dto.PokemonResponse
import com.tomljanovic.matko.pokedex.util.Const
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeDexApi {
    @GET(Const.Api.POKEMON)
    suspend fun getListOfPokemon(
        @Query(Const.Api.LIMIT) limit: Int
    ): PokemonListResponse

    @GET(Const.Api.POKEMON_WITH_ID)
    suspend fun getPokemonById(
        @Path(Const.Api.NAME) name: String
    ): PokemonResponse

    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }
}