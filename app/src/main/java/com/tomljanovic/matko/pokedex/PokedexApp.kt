package com.tomljanovic.matko.pokedex

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tomljanovic.matko.pokedex.navigation.PokedexDestinations
import com.tomljanovic.matko.pokedex.navigation.PokedexNavHost
import com.tomljanovic.matko.pokedex.navigation.PokedexNavigationActions
import com.tomljanovic.matko.pokedex.presentation.PokeDexViewModel
import com.tomljanovic.matko.pokedex.presentation.PokedexTopBar
import com.tomljanovic.matko.pokedex.presentation.SearchBarState
import com.tomljanovic.matko.pokedex.presentation.TopAppBarState
import com.tomljanovic.matko.pokedex.ui.theme.PokeDexTheme

@Composable
fun PokedexApp() {
    PokeDexTheme {
        val navController = rememberNavController()
        val viewModel = hiltViewModel<PokeDexViewModel>()

        var topAppBarState by remember {
            mutableStateOf(TopAppBarState())
        }
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        topAppBarState = when (currentRoute) {
            PokedexDestinations.Home.name -> {
                TopAppBarState(
                    showLogo = true,
                    onSearchClick = {
                        topAppBarState = topAppBarState.copy(showSearchBar = SearchBarState.OPEN)
                    }
                )
            }

            PokedexDestinations.Details.name -> {
                topAppBarState.copy(
                    title = stringResource(R.string.details),
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                PokedexNavigationActions(navController).navigateToHome()
                            }
                        ) {
                            Icon(
                                tint = MaterialTheme.colorScheme.primaryContainer,
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back)
                            )
                        }
                    },
                    showLogo = false,
                    showSearchBar = SearchBarState.REMOVED
                )
            }

            else -> TopAppBarState()
        }

        Scaffold(
            contentWindowInsets = WindowInsets.safeContent,
            topBar = {
                PokedexTopBar(
                    topAppBarState = topAppBarState,
                    searchValue = viewModel.searchQuery.value,
                    onSearchValueChange = {
                        viewModel.updateSearchQuery(it)
                    },
                    onSearchClick = { idOrName ->
                        viewModel.searchForPokemon(idOrName.toLowerCase(Locale.current))
                    }
                )
            },
            modifier = Modifier
                .fillMaxSize()
        ) { innerPadding ->
            PokedexNavHost(
                navController = navController,
                innerPadding = innerPadding,
                topAppBarState = topAppBarState,
                onTopAppBarStateChange = {
                    topAppBarState = it
                },
                viewModel = viewModel
            )
        }
    }
}