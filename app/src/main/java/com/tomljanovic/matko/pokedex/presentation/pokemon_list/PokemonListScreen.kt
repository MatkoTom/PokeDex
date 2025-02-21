package com.tomljanovic.matko.pokedex.presentation.pokemon_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.tomljanovic.matko.pokedex.domain.model.Pokemon
import com.tomljanovic.matko.pokedex.util.Tools

@Composable
fun PokemonListScreen(
    modifier: Modifier = Modifier,
    onPokemonClick: (Pokemon) -> Unit,
    viewModel: PokeDexViewModel
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getPokeDexEntries(151)
    }

    val state by viewModel.pokeDexState.collectAsState()

    if (state.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.Black.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {

            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 4.dp
            )
        }
    }
    PokedexGrid(pokemon = state.pokemonList, onPokemonClick)
}

@Composable
fun PokedexGrid(
    pokemon: List<Pokemon>,
    onPokemonClick: (Pokemon) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {
        items(pokemon) { poke ->
            PokedexItem(
                poke,
                onPokemonClick = onPokemonClick
            )
        }
    }
}

@Preview
@Composable
fun PokedexGridPreview() {
    PokedexGrid(
        pokemon = listOf(
            Pokemon(
                id = 1,
                name = "Bulbasaur",
                stats = emptyMap(),
                types = listOf("grass", "poison"),
                sprite = ""
            ),
            Pokemon(
                id = 2,
                name = "Ivysaur",
                stats = emptyMap(),
                types = listOf("grass", "poison"),
                sprite = ""
            ),
            Pokemon(
                id = 3,
                name = "Venusaur",
                stats = emptyMap(),
                types = listOf("grass", "poison"),
                sprite = ""
            ),
            Pokemon(
                id = 4,
                name = "Charmander",
                stats = emptyMap(),
                types = listOf("fire"),
                sprite = ""
            ),
            Pokemon(
                id = 5,
                name = "Charmeleon",
                stats = emptyMap(),
                types = listOf("fire"),
                sprite = ""
            ),
            Pokemon(
                id = 6,
                name = "Charizard",
                stats = emptyMap(),
                types = listOf("fire", "flying"),
                sprite = ""
            ),
            Pokemon(
                id = 7,
                name = "Squirtle",
                stats = emptyMap(),
                types = listOf("water"),
                sprite = ""
            )
        )
    )
}

@Composable
fun PokedexItem(
    pokemon: Pokemon,
    onPokemonClick: (Pokemon) -> Unit
) {
    val cardColours = if (pokemon.types.size == 1) {
        listOf(Tools.typeColorList(pokemon.types)[0], Tools.typeColorList(pokemon.types)[0])
    } else Tools.typeColorList(pokemon.types)

    val capitalizedText = pokemon.name.replaceFirstChar { it.uppercase() }

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
                model = pokemon.sprite,
                contentDescription = null,
            )

            Text(
                text = capitalizedText,
                color = Color.White,
                textAlign = TextAlign.Center,
                maxLines = 1,
                fontSize = 12.sp
            )
        }
    }
}

@Preview
@Composable
fun PokedexItemPreview() {
    PokedexItem(
        Pokemon(
            id = 1,
            name = "Bulbasaur",
            stats = emptyMap(),
            types = listOf("grass", "poison"),
            sprite = "https://raw.githubuserco…r/official-artwork/1.png"
        ),
        onPokemonClick = {}
    )
}