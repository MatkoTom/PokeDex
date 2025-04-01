package com.tomljanovic.matko.pokedex.presentation.components.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tomljanovic.matko.pokedex.R
import com.tomljanovic.matko.pokedex.util.Tools.removeLeadingZeros

@Composable
fun PokedexSearch(
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    onSearchClick: (String) -> Unit,
    focusRequester: FocusRequester
) {
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
            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
            focusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchClick(searchValue)
            }
        )
    )
}