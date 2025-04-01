package com.tomljanovic.matko.pokedex.util

import androidx.compose.ui.graphics.Color

object Tools {
    fun typeColorList(pokemonType: List<String>): List<Color> {
        return pokemonType.map { type ->
            typeColors[type] ?: Color.White
        }
    }

    fun typeColor(type: String): Color {
        return typeColors[type] ?: Color.White
    }

    fun statColor(stat: String): Color {
        return statsColors[stat] ?: Color.White
    }

    fun statName(stat: String): String {
        return statNamesMapper[stat] ?: ""
    }

    private val statNamesMapper = mapOf(
        "hp" to "hp",
        "attack" to "atk",
        "defense" to "def",
        "special-attack" to "spa",
        "special-defense" to "spd",
        "speed" to "spe"
    )

    private val statsColors = mapOf(
        "hp" to Color(0xFF69DC12),
        "atk" to Color(0xFFEFCC18),
        "def" to Color(0xFFE86412),
        "spa" to Color(0xFF14C3F1),
        "spd" to Color(0xFF4A6ADF),
        "spe" to Color(0xFFD51DAD)
    )

    private val typeColors = mapOf(
        "normal" to Color(0xFF9FA19F),
        "fire" to Color(0xFFE62829),
        "fighting" to Color(0xFFFF8000),
        "water" to Color(0xFF2980EF),
        "flying" to Color(0xFF81B9EF),
        "grass" to Color(0xFF3FA129),
        "poison" to Color(0xFF9141CB),
        "electric" to Color(0xFFFAC000),
        "ground" to Color(0xFF915121),
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

    fun formatNumber(number: Int): String {
        return String.format("#%03d", number)
    }

    fun removeLeadingZeros(input: String): String {
        if (input.isEmpty()) {
            return ""
        }

        val trimmed = input.trimStart('0')
        return trimmed.ifEmpty { "0" }
    }
}