package com.tomljanovic.matko.pokedex.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: List<PokemonEntity>)

    @Query("SELECT * FROM pokemonentity")
    suspend fun getLocalPokemon(): List<PokemonEntity>

    @Query("DELETE FROM pokemonentity")
    suspend fun clearPokemon()
}