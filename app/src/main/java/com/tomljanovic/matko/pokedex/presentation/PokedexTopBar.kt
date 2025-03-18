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
                    color = Color.White,
                    text = topAppBarState.title
                )
            }
        },
        navigationIcon = { topAppBarState.navigationIcon?.invoke() },
        actions = {
            if (SearchBarState.OPEN == topAppBarState.showSearchBar) {
                TextField(
                    modifier = Modifier
                        .widthIn(max = 200.dp)
                        .padding(all = 8.dp)
                        .focusRequester(focusRequester),
                    value = searchValue,
                    onValueChange = {
                        val formattedText = removeLeadingZeros(it)

                        if (formattedText != "0")
                            onSearchValueChange(formattedText)
                    },
                    placeholder = {
                        Text(text = stringResource(R.string.search_hint), fontSize = 14.sp)
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        focusedPlaceholderColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            onSearchClick(searchValue)
                        }
                    )
                )
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
            } else if (SearchBarState.CLOSED == topAppBarState.showSearchBar) {
                IconButton(onClick = {
                    topAppBarState.onSearchClick()
                }) {
                    Icon(
                        tint = Color.White,
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(R.string.back)
                    )
                }
                topAppBarState.actions?.invoke()
            } else {
                topAppBarState.actions?.invoke()
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = topAppBarState.pokemonBackground
        )
    )
}