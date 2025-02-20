package com.tomljanovic.matko.pokedex.util

import androidx.compose.ui.graphics.Color

object Tools {
    fun typeColor(pokemonType: List<String>): List<Color> {
        return pokemonType.map { type ->
            typeColors[type] ?: Color.White
        }
    }

    private val typeColors = mapOf(
        "normal" to Color(0xFF9FA19F),
        "fire" to Color(0xFFE62829),
        "fighting" to Color(0xFFFFA500),
        "water" to Color(0xFF2980EF),
        "flying" to Color(0xFF81B9EF),
        "grass" to Color(0xFF3FA129),
        "poison" to Color(0xFF9D00FF),
        "electric" to Color(0xFFFAC000),
        "ground" to Color(0xFF964B00),
        "psychic" to Color(0xFFEF4179),
        "rock" to Color(0xFFAFA981),
        "ice" to Color(0xFF3DCEF3),
        "bug" to Color(0xFF91A119),
        "dragon" to Color(0xFF5060E1),
        "ghost" to Color(0xFF704170),
        "dark" to Color(0xFF624D4E),
        "steel" to Color(0xFF60A1B8),
        "fairy" to Color(0xFFEF70EF),
    )
}