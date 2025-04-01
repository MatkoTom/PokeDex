package com.tomljanovic.matko.pokedex.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tomljanovic.matko.pokedex.R
import com.tomljanovic.matko.pokedex.presentation.components.utils.PokedexSearch
import com.tomljanovic.matko.pokedex.util.Tools.removeLeadingZeros

data class TopAppBarState(
    val title: String = "",
    val navigationIcon: (@Composable () -> Unit)? = null,
    val pokemonBackground: Color = Color.Transparent,
    val actions: (@Composable () -> Unit)? = null,
    val showLogo: Boolean = true,
    val showSearchBar: SearchBarState = SearchBarState.CLOSED,
    val onSearchClick: () -> Unit = {}
)

enum class SearchBarState {
    CLOSED,
    OPEN,
    REMOVED
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokedexTopBar(
    topAppBarState: TopAppBarState,
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    onSearchClick: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
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
                    color = MaterialTheme.colorScheme.onPrimary,
                    text = topAppBarState.title
                )
            }
        },
        navigationIcon = { topAppBarState.navigationIcon?.invoke() },
        actions = {
            when (topAppBarState.showSearchBar) {
                SearchBarState.OPEN -> {
                    PokedexSearch(
                        searchValue = searchValue,
                        onSearchValueChange = {
                           onSearchValueChange(it)
                        },
                        onSearchClick = {
                            onSearchClick(searchValue)
                        },
                        focusRequester = focusRequester
                    )
                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                    }
                }

                SearchBarState.CLOSED -> {
                    IconButton(onClick = {
                        topAppBarState.onSearchClick()
                    }) {
                        Icon(
                            tint = MaterialTheme.colorScheme.primaryContainer,
                            imageVector = Icons.Filled.Search,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                    topAppBarState.actions?.invoke()
                }

                else -> {
                    topAppBarState.actions?.invoke()
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = topAppBarState.pokemonBackground
        )
    )
}