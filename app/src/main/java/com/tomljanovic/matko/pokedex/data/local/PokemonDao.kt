package com.tomljanovic.matko.pokedex.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: PokemonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: List<PokemonEntity>)

    @Query("SELECT * FROM pokemonentity")
    suspend fun getLocalPokemon(): List<PokemonEntity>

    @Query("SELECT * FROM pokemonentity WHERE id = :nameOrId OR name = :nameOrId")
    suspend fun getLocalPokemon(nameOrId: String): PokemonEntity?

    @Query("DELETE FROM pokemonentity WHERE id = :id")
    suspend fun deletePokemon(id: Int)

    @Query("SELECT MAX(id) FROM pokemonentity")
    suspend fun getMaxPokemonId(): Int?

    @Query("DELETE FROM pokemonentity")
    suspend fun clearPokemon()
}