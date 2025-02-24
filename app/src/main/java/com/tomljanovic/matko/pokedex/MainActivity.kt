package com.tomljanovic.matko.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tomljanovic.matko.pokedex.presentation.details.PokemonDetailsScreen
import com.tomljanovic.matko.pokedex.presentation.pokemon_list.PokeDexViewModel
import com.tomljanovic.matko.pokedex.presentation.pokemon_list.PokemonListScreen
import com.tomljanovic.matko.pokedex.ui.theme.PokeDexTheme
import com.tomljanovic.matko.pokedex.util.Tools
import dagger.hilt.android.AndroidEntryPoint

data class TopAppBarState(
    val title: String = "",
    val navigationIcon: (@Composable () -> Unit)? = null,
    val pokemonBackground: Color = Color.Transparent,
    val actions: (@Composable () -> Unit)? = null,
    val showLogo: Boolean = true
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokeDexTheme {
                val navController = rememberNavController()
                val viewModel = hiltViewModel<PokeDexViewModel>()

                var topAppBarState by remember {
                    mutableStateOf(TopAppBarState())
                }
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                topAppBarState = when (currentRoute) {
                    PokemonListNav.Home.name -> {
                        TopAppBarState(
                            showLogo = true
                        )
                    }

                    PokemonListNav.Details.name -> {
                        topAppBarState.copy(
                            title = "Details",
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        navController.popBackStack()
                                    }
                                ) {
                                    Icon(
                                        tint = Color.White,
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back"
                                    )
                                }
                            },
                            showLogo = false
                        )
                    }

                    else -> TopAppBarState()
                }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                if (topAppBarState.showLogo) {
                                    Image(
                                        painter = painterResource(id = R.drawable.pokemon_logo),
                                        contentDescription = null,
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier.size(size = 128.dp)
                                    )
                                } else {
                                    Text(color = Color.White,
                                        text = topAppBarState.title)
                                }
                            },
                            navigationIcon = { topAppBarState.navigationIcon?.invoke() },
                            actions = { topAppBarState.actions?.invoke() },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = topAppBarState.pokemonBackground
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = PokemonListNav.Home.name,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = PokemonListNav.Home.name) {
                            PokemonListScreen(
                                onPokemonClick = {
                                    topAppBarState =
                                        topAppBarState.copy(pokemonBackground = Tools.typeColor(it.types[0]),
                                            actions = {
                                                Text(
                                                    text = Tools.formatNumber(it.id),
                                                    modifier = Modifier.padding(end = 16.dp),
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.White,
                                                    style = MaterialTheme.typography.titleLarge
                                                )
                                            })
                                    viewModel.updatePokemonSelected(it)
                                    navController.navigate(PokemonListNav.Details.name)
                                },
                                viewModel = viewModel
                            )
                        }
                        composable(route = PokemonListNav.Details.name) {
                            PokemonDetailsScreen(
                                viewModel = viewModel
                            )
                        }
                    }

                }
            }
        }
    }
}