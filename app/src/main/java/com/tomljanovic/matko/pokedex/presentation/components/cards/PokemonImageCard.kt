package com.tomljanovic.matko.pokedex.presentation.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.tomljanovic.matko.pokedex.domain.model.Pokemon
import com.tomljanovic.matko.pokedex.util.Tools

@Composable
fun PokemonImageCard(pokemon: Pokemon) {
    val cardColours = if (pokemon.types.size == 1) {
        listOf(Tools.typeColorList(pokemon.types)[0], Tools.typeColorList(pokemon.types)[0])
    } else Tools.typeColorList(pokemon.types)

    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .size(size = 256.dp)
                .padding(bottom = 24.dp),
            shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = cardColours
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = pokemon.sprite,
                    contentDescription = null,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PokemonImageCardPreview() {
    PokemonImageCard(
        Pokemon(
            id = 1,
            name = "Bulbasaur",
            stats = emptyMap(),
            types = listOf("grass", "poison"),
            sprite = ""
        )
    )
}