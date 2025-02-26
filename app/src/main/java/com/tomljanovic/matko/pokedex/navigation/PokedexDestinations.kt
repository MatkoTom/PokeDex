package com.tomljanovic.matko.pokedex.navigation

import androidx.navigation.NavHostController

enum class PokedexDestinations {
    Home,
    Details
}

class PokedexNavigationActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(PokedexDestinations.Home.name) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
        }
    }

    val navigateToDetails: () -> Unit = {
        navController.navigate(PokedexDestinations.Details.name) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
        }
    }
}