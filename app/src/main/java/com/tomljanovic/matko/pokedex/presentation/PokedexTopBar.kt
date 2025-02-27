package com.tomljanovic.matko.pokedex.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tomljanovic.matko.pokedex.R

data class TopAppBarState(
    val title: String = "",
    val navigationIcon: (@Composable () -> Unit)? = null,
    val pokemonBackground: Color = Color.Transparent,
    val actions: (@Composable () -> Unit)? = null,
    val showLogo: Boolean = true
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokedexTopBar(
    topAppBarState: TopAppBarState
) {
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
                Text(
                    color = Color.White,
                    text = topAppBarState.title
                )
            }
        },
        navigationIcon = { topAppBarState.navigationIcon?.invoke() },
        actions = { topAppBarState.actions?.invoke() },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = topAppBarState.pokemonBackground
        )
    )
}