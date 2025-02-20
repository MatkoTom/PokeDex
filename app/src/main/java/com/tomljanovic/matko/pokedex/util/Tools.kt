package com.tomljanovic.matko.pokedex.util

import androidx.compose.ui.graphics.Color

object Tools {
    fun typeColor(pokemonType: List<String>): List<Color> {
        return pokemonType.map { type ->
            typeColors[type] ?: Color.White
        }
    }

    private val typeColors = mapOf(
        "normal" to Color.Gray,
        "fire" to Color.Red,
        "fighting" to Color(0xFFFFA500),
        "water" to Color.Blue,
        "flying" to Color(0xFF81B9EF),
        "grass" to Color.Green,
        "poison" to Color(0xFF9D00FF),
        "electric" to Color.Yellow,
        "ground" to Color(0xFF964B00),
        "psychic" to Color(0xFFEF4179),
        "rock" to Color.LightGray,
        "ice" to Color.Cyan,
        "bug" to Color(0xFF91A119),
        "dragon" to Color(0xFF5060E1),
        "ghost" to Color(0xFF704170),
        "dark" to Color.Black,
        "steel" to Color(0xFF60A1B8),
        "fairy" to Color.Magenta,
    )
}