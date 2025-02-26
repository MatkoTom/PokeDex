package com.tomljanovic.matko.pokedex.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tomljanovic.matko.pokedex.TopAppBarState
import com.tomljanovic.matko.pokedex.presentation.PokeDexViewModel
import com.tomljanovic.matko.pokedex.presentation.details.PokemonDetailsScreen
import com.tomljanovic.matko.pokedex.presentation.pokemon_list.PokemonListScreen
import com.tomljanovic.matko.pokedex.util.Tools

@Composable
fun PokedexNavHost(
    navController: NavHostController,
    innerPadding: PaddingValues,
    topAppBarState: TopAppBarState,
    onTopAppBarStateChange: (TopAppBarState) -> Unit = {},
    viewModel: PokeDexViewModel
) {
    NavHost(
        navController = navController,
        startDestination = PokedexDestinations.Home.name,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = PokedexDestinations.Home.name) {
            PokemonListScreen(
                onPokemonClick = {
                    onTopAppBarStateChange(
                        topAppBarState.copy(
                            pokemonBackground = Tools.typeColor(it.types[0]),
                            actions = {
                                Text(
                                    text = Tools.formatNumber(it.id),
                                    modifier = Modifier.padding(end = 16.dp),
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    style = MaterialTheme.typography.titleLarge
                                )
                            })
                    )
                    viewModel.updatePokemonSelected(it)
                    PokedexNavigationActions(navController).navigateToDetails()
                },
                viewModel = viewModel
            )
        }
        composable(route = PokedexDestinations.Details.name) {
            PokemonDetailsScreen(
                viewModel = viewModel
            )
        }
    }
}