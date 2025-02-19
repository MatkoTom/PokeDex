package com.tomljanovic.matko.pokedex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PokemonEntity::class],
    version = 1
)
abstract class PokedexDatabase: RoomDatabase() {
    abstract val dao: PokemonDao
}