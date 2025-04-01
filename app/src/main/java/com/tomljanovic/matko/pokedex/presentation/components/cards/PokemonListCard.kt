package com.tomljanovic.matko.pokedex.presentation.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.tomljanovic.matko.pokedex.domain.model.Pokemon

@Composable
fun PokemonListCard(
    pokemon: Pokemon,
    cardColours: List<Color>,
    capitalizedText: String,
    onPokemonClick: (Pokemon) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 4.dp)
            .size(size = 156.dp),
        shape = RoundedCornerShape(size = 8.dp),
        onClick = { onPokemonClick(pokemon) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = cardColours
                    )
                )
                .padding(all = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                modifier = Modifier.weight(1f),
                model = pokemon.sprite,
                contentDescription = null,
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = capitalizedText,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontSize = 12.sp
            )
        }
    }
}

@Preview
@Composable
fun PokedexListCardPreview() {
    PokemonListCard(
        Pokemon(
            id = 1,
            name = "Bulbasaur",
            stats = emptyMap(),
            types = listOf("grass", "poison"),
            sprite = "https://raw.githubuserco…r/official-artwork/1.png",
            animatedSprite = ""
        ),
        cardColours = listOf(Color.Green, Color.Cyan),
        capitalizedText = "Bulbasaur",
        onPokemonClick = {}
    )
}