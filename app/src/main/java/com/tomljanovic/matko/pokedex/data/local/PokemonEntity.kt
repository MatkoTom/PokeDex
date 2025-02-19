package com.tomljanovic.matko.pokedex.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonEntity(
    val name: String,

    @PrimaryKey
    val id: Int? = null
)
