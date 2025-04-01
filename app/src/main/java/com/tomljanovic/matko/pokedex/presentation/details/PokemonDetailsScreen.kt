package com.tomljanovic.matko.pokedex.presentation.details

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomljanovic.matko.pokedex.R
import com.tomljanovic.matko.pokedex.domain.model.Pokemon
import com.tomljanovic.matko.pokedex.presentation.PokeDexViewModel
import com.tomljanovic.matko.pokedex.presentation.components.cards.PokemonImageCard
import com.tomljanovic.matko.pokedex.util.Tools

@Composable
fun PokemonDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: PokeDexViewModel
) {
    val state by viewModel.pokeDexState.collectAsStateWithLifecycle()

    state.pokemon?.let {
        Column {
            PokemonImageCard(it)
            PokemonInformation(it)
        }
    }
}

@Composable
fun PokemonInformation(pokemon: Pokemon) {
    Column(
        modifier = Modifier
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            pokemon.name.replaceFirstChar { it.uppercase() },
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )

        Row {
            pokemon.types.map { type ->
                TypeContainer(type)
            }
        }

        Text(
            text = stringResource(R.string.base_stats),
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            pokemon.stats.forEach { (name, value) ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = Tools.statName(name).uppercase(),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    StatsContainer(
                        modifier = Modifier.weight(8f),
                        name = Tools.statName(name),
                        value = value
                    )
                }
            }
        }
    }
}

@Composable
fun StatsContainer(modifier: Modifier = Modifier, name: String, value: Int) {
    var isAnimationStarted by remember {
        mutableStateOf(false)
    }
    val animatedProgress by animateFloatAsState(
        targetValue = if (isAnimationStarted)
            value.toFloat() / 255.0f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )

    LaunchedEffect(key1 = true) {
        isAnimationStarted = true
    }

    val containerColor = MaterialTheme.colorScheme.primaryContainer
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
            .drawBehind {
                // Empty white container
                drawRoundRect(
                    color = containerColor,
                    cornerRadius = CornerRadius(24.dp.toPx(), 24.dp.toPx())
                )

                // Filled container with animation
                drawRoundRect(
                    color = Tools.statColor(name),
                    cornerRadius = CornerRadius(24.dp.toPx(), 24.dp.toPx()),
                    size = Size(
                        width = size.width * animatedProgress,
                        height = size.height
                    )
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(animatedProgress + 0.14f)
                .padding(end = 8.dp)
                .align(Alignment.CenterStart),
            textAlign = TextAlign.End,
            text = value.toString(),
            color = MaterialTheme.colorScheme.onSecondary,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun TypeContainer(type: String) {
    Box(
        modifier = Modifier
            .padding(all = 8.dp)
            .size(width = 128.dp, height = 24.dp)
            .clip(
                RoundedCornerShape(size = 24.dp)
            )
            .background(Tools.typeColor(type)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = type.replaceFirstChar { it.uppercase() }, color = MaterialTheme.colorScheme.onPrimary)
    }
}

@Preview
@Composable
fun StatsContainerPreview() {
    StatsContainer(name = "hp", value = 255)
}

@Preview
@Composable
fun PokemonInformationPreview() {
    PokemonInformation(
        Pokemon(
            id = 1,
            name = "Bulbasaur",
            stats = mapOf(
                "hp" to 12,
                "attack" to 162,
                "defense" to 221
            ),
            types = listOf("grass", "poison"),
            sprite = "",
            animatedSprite = ""
        )
    )
}

@Preview
@Composable
fun TypeContainerPreview() {
    TypeContainer(type = "grass")
}