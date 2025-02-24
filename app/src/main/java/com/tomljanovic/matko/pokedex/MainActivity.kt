package com.tomljanovic.matko.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokedexApp()
        }
    }
}