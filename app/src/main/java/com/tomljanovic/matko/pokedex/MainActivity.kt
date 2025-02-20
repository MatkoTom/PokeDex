package com.tomljanovic.matko.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.tomljanovic.matko.pokedex.domain.model.Pokemon
import com.tomljanovic.matko.pokedex.presentation.PokeDexViewModel
import com.tomljanovic.matko.pokedex.ui.theme.PokeDexTheme
import com.tomljanovic.matko.pokedex.util.Tools
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokeDexTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding()
                ) { innerPadding ->
                    Pokedex(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Pokedex(
    modifier: Modifier = Modifier,
    viewModel: PokeDexViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getPokeDexEntries(10)
    }

    val state = viewModel.pokeDexState

    PokedexGrid(pokemon = state.pokemon)
}

@Composable
fun PokedexGrid(pokemon: List<Pokemon>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {
        items(pokemon) { poke ->
            PokedexItem(poke)
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
fun PokedexItem(pokemon: Pokemon) {
    val cardColours = if (pokemon.types.size == 1) {
        listOf(Tools.typeColor(pokemon.types)[0], Tools.typeColor(pokemon.types)[0])
    } else Tools.typeColor(pokemon.types)

    val capitalizedText = pokemon.name.replaceFirstChar { it.uppercase() }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
            .size(size = 128.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = cardColours
                )
            ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
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
        )
    )
}