package com.tomljanovic.matko.pokedex.presentation.pokemon_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomljanovic.matko.pokedex.domain.model.Pokemon
import com.tomljanovic.matko.pokedex.presentation.PokeDexViewModel
import com.tomljanovic.matko.pokedex.presentation.components.cards.PokemonListCard
import com.tomljanovic.matko.pokedex.util.Tools
import timber.log.Timber

const val INITIAL_LIST_LIMIT = 20
const val NEXT_PAGE_LIMIT = 30

@Composable
fun PokemonListScreen(
    modifier: Modifier = Modifier,
    onPokemonClick: (Pokemon) -> Unit,
    viewModel: PokeDexViewModel
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getPokeDexEntries(numberOfPokemon = INITIAL_LIST_LIMIT)
    }

    val state by viewModel.pokeDexState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery

    if (searchQuery.isNotEmpty()) {
        Timber.d("Search query = $searchQuery")
    }

    Box(modifier = modifier.fillMaxSize()) {
        PokedexGrid(pokemon = state.pokemonList, onPokemonClick, searchQuery = searchQuery)

        if (state.isLoading) {
            Box(
                modifier = Modifier
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
    }
}

@Composable
fun PokedexGrid(
    pokemon: List<Pokemon>,
    onPokemonClick: (Pokemon) -> Unit = {},
    viewModel: PokeDexViewModel = hiltViewModel(),
    searchQuery: String = ""
) {
    val scrollState = rememberLazyGridState()
    val endReached = checkIfLastItemIsVisible(scrollState)

    if (endReached) {
        LaunchedEffect(key1 = Unit) {
            // TODO Fix this so API isn't called every time the list gets filtered.
            if (viewModel.searchQuery.value.isEmpty()) {
//                viewModel.getNextSetOfPokemon(limit = NEXT_PAGE_LIMIT)
            }
        }
    }

    var pokeItems = pokemon
    if (searchQuery.isNotEmpty()) {
        pokeItems = pokeItems.filter {
            it.name.contains(searchQuery)
        }
    }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        state = scrollState
    ) {
        items(pokeItems) { poke ->
            PokedexItem(
                poke,
                onPokemonClick = onPokemonClick
            )
        }
    }
}

@Composable
fun checkIfLastItemIsVisible(scrollState: LazyGridState): Boolean {
    val endReached by remember {
        derivedStateOf {
            val layoutInfo = scrollState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0)

            lastVisibleItemIndex >= totalItemsNumber - 1 && totalItemsNumber > 0
        }
    }
    return endReached
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

    PokemonListCard(
        pokemon = pokemon,
        cardColours = cardColours,
        capitalizedText = capitalizedText,
        onPokemonClick = onPokemonClick
    )
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
                sprite = "",
                animatedSprite = ""
            ),
            Pokemon(
                id = 2,
                name = "Ivysaur",
                stats = emptyMap(),
                types = listOf("grass", "poison"),
                sprite = "",
                animatedSprite = ""
            ),
            Pokemon(
                id = 3,
                name = "Venusaur",
                stats = emptyMap(),
                types = listOf("grass", "poison"),
                sprite = "",
                animatedSprite = ""
            ),
            Pokemon(
                id = 4,
                name = "Charmander",
                stats = emptyMap(),
                types = listOf("fire"),
                sprite = "",
                animatedSprite = ""
            ),
            Pokemon(
                id = 5,
                name = "Charmeleon",
                stats = emptyMap(),
                types = listOf("fire"),
                sprite = "",
                animatedSprite = ""
            ),
            Pokemon(
                id = 6,
                name = "Charizard",
                stats = emptyMap(),
                types = listOf("fire", "flying"),
                sprite = "",
                animatedSprite = ""
            ),
            Pokemon(
                id = 7,
                name = "Squirtle",
                stats = emptyMap(),
                types = listOf("water"),
                sprite = "",
                animatedSprite = ""
            )
        )
    )
}