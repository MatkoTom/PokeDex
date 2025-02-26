package com.tomljanovic.matko.pokedex.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.tomljanovic.matko.pokedex.util.Converters

@Entity
@TypeConverters(Converters::class)
data class PokemonEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val stats: Map<String, Int>,
    val types: List<String>,
    val sprite: String,
    val animatedSprite: String
)
