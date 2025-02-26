package com.tomljanovic.matko.pokedex.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomljanovic.matko.pokedex.domain.model.Pokemon
import com.tomljanovic.matko.pokedex.domain.repository.PokeDexRepository
import com.tomljanovic.matko.pokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokeDexViewModel @Inject constructor(
    private val repository: PokeDexRepository
) : ViewModel() {
    private val _pokeDexState = MutableStateFlow(PokeDexState())
    val pokeDexState: StateFlow<PokeDexState> = _pokeDexState.asStateFlow()

    fun getPokeDexEntries(
        numberOfPokemon: Int,
        fetchFromRemote: Boolean = false
    ) = viewModelScope.launch {
        repository.getPokemonList(numberOfPokemon, fetchFromRemote).collect { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { pokemonList ->
                        _pokeDexState.update {
                            it.copy(
                                pokemonList = pokemonList
                            )
                        }
                    }
                }

                is Resource.Error -> {
                    result.message?.let { error ->
                        _pokeDexState.update {
                            it.copy(
                                error = error
                            )
                        }
                    }
                }

                is Resource.Loading -> {
                    _pokeDexState.update {
                        it.copy(
                            isLoading = result.isLoading
                        )
                    }
                }
            }
        }
    }

    fun getNextSetOfPokemon(limit: Int) = viewModelScope.launch {
        repository.getNextPage(limit).collect { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { pokemonList ->
                        _pokeDexState.update {
                            it.copy(
                                pokemonList = pokemonList
                            )
                        }
                    }
                }

                is Resource.Error -> {
                    result.message?.let { error ->
                        _pokeDexState.update {
                            it.copy(
                                error = error
                            )
                        }
                    }
                }

                is Resource.Loading -> {
                    _pokeDexState.update {
                        it.copy(
                            isLoading = result.isLoading
                        )
                    }
                }
            }

        }
    }

    fun updatePokemonSelected(pokemon: Pokemon) {
        _pokeDexState.update {
            it.copy(
                pokemon = pokemon
            )
        }
    }
}