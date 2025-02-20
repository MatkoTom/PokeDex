package com.tomljanovic.matko.pokedex.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomljanovic.matko.pokedex.domain.repository.PokeDexRepository
import com.tomljanovic.matko.pokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokeDexViewModel @Inject constructor(
    private val repository: PokeDexRepository
) : ViewModel() {
    var pokeDexState by mutableStateOf(PokeDexState())

    fun getPokeDexEntries(
        numberOfPokemon: Int,
        fetchFromRemote: Boolean = false
    ) = viewModelScope.launch {
        repository.getPokemonList(numberOfPokemon, fetchFromRemote).collect { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let {
                        pokeDexState = pokeDexState.copy(pokemon = it)
                    }
                }

                is Resource.Error -> {
                    result.message?.let {
                        pokeDexState = pokeDexState.copy(error = it)
                    }
                }

                is Resource.Loading -> {
                    pokeDexState =
                        pokeDexState.copy(isLoading = result.isLoading)
                }
            }
        }
    }
}