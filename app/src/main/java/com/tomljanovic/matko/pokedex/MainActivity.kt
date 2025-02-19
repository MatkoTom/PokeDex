package com.tomljanovic.matko.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tomljanovic.matko.pokedex.domain.model.Pokemon
import com.tomljanovic.matko.pokedex.presentation.PokeDexViewModel
import com.tomljanovic.matko.pokedex.ui.theme.PokeDexTheme
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
            PokedexItem(poke.name)
        }
    }
}

@Preview
@Composable
fun PokedexGridPreview() {
    PokedexGrid(
        pokemon = listOf(
            Pokemon(name = "Bulbasaur"),
            Pokemon(name = "Ivysaur"),
            Pokemon(name = "Venusaur"),
            Pokemon(name = "Charmander"),
            Pokemon(name = "Charmeleon"),
            Pokemon(name = "Charizard"),
            Pokemon(name = "Squirtle"),
            Pokemon(name = "Wartortle"),
        )
    )
}

@Composable
fun PokedexItem(name: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
            .size(size = 64.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Yellow)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            val capitalizedText = name.replaceFirstChar { it.uppercase() }

            Text(
                text = capitalizedText,
                color = Color.Black,
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
    PokedexItem(name = "Bulbasaur")
}