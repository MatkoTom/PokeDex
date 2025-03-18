package com.tomljanovic.matko.pokedex.data.repository

import android.app.Application
import com.tomljanovic.matko.pokedex.R
import com.tomljanovic.matko.pokedex.data.local.PokedexDatabase
import com.tomljanovic.matko.pokedex.data.mappers.toPokemon
import com.tomljanovic.matko.pokedex.data.mappers.toPokemonEntity
import com.tomljanovic.matko.pokedex.data.remote.PokeDexApi
import com.tomljanovic.matko.pokedex.domain.model.Pokemon
import com.tomljanovic.matko.pokedex.domain.repository.PokeDexRepository
import com.tomljanovic.matko.pokedex.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokeDexRepositoryImpl @Inject constructor(
    private val context: Application,
    private val pokeDexApi: PokeDexApi,
    pokedexDb: PokedexDatabase
) : PokeDexRepository {
    private val pokedexDao = pokedexDb.dao

    override fun getPokemonList(limit: Int, isFromRemote: Boolean): Flow<Resource<List<Pokemon>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))

            val localPokemon = pokedexDao.getLocalPokemon()
            emit(Resource.Success(data = localPokemon.map { it.toPokemon() }))

            val isDbEmpty = localPokemon.isEmpty()
            val shouldLoadFromCache = !isDbEmpty && !isFromRemote
            if (shouldLoadFromCache) {
                emit(Resource.Loading(isLoading = false))
                return@flow
            }

            val remotePokemon = try {
                pokeDexApi.getListOfPokemon(limit).results
            } catch (e: HttpException) {
                Timber.e("Fetch error: ${e.message}")
                emit(Resource.Error(message = context.getString(R.string.unable_to_load_data_remote)))
                null
            } catch (e: IOException) {
                Timber.e("Fetch error local: ${e.message}")
                emit(Resource.Error(message = context.getString(R.string.unable_to_load_data_local)))
                null
            }

            remotePokemon?.let { pokemon ->
                coroutineScope {
                    val deferredResults = pokemon.map {
                        async(Dispatchers.IO) {
                            getSpecificPokemon(it.name)
                        }
                    }
                    deferredResults.awaitAll()
                }
                emit(Resource.Success(data = pokedexDao.getLocalPokemon().map { it.toPokemon() }))
            }
            emit(Resource.Loading(isLoading = false))
        }
    }

    override fun getNextPage(limit: Int): Flow<Resource<List<Pokemon>>> {
        return flow {
            emit(Resource.Loading(true))

            val lastPokemonInDb = pokedexDao.getMaxPokemonId()

            lastPokemonInDb?.let { id ->
                coroutineScope {
                    val deferredResults = (id + 1..id + limit).map {
                        async(Dispatchers.IO) {
                            getSpecificPokemon(it.toString())
                        }
                    }
                    deferredResults.awaitAll()
                }
                emit(Resource.Success(data = pokedexDao.getLocalPokemon().map { it.toPokemon() }))
            }
            emit(Resource.Loading(isLoading = false))
        }
    }

    override fun searchForPokemon(nameOrId: String): Flow<Resource<List<Pokemon>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))

            val localPokemon = pokedexDao.getLocalPokemon(nameOrId)

            val isDbEmpty = localPokemon == null
            val shouldLoadFromCache = !isDbEmpty
            if (shouldLoadFromCache) {
                emit(Resource.Loading(isLoading = false))
                return@flow
            }

            val remotePokemon = try {
                pokeDexApi.getPokemonById(nameOrId)
            } catch (e: HttpException) {
                Timber.e("Fetch error: ${e.message}")
                emit(Resource.Error(message = context.getString(R.string.unable_to_find_pokemon)))
                null
            } catch (e: IOException) {
                Timber.e("Fetch error local: ${e.message}")
                emit(Resource.Error(message = context.getString(R.string.unable_to_load_data_local)))
                null
            }

            remotePokemon?.let { pokemon ->
                pokedexDao.insertPokemon(pokemon.toPokemonEntity())
                emit(Resource.Success(data = pokedexDao.getLocalPokemon().map { it.toPokemon() }))
            }
            emit(Resource.Loading(isLoading = false))
        }
    }

    private suspend fun getSpecificPokemon(name: String) {
        val remotePokemon = try {
            pokeDexApi.getPokemonById(name)
        } catch (e: HttpException) {
            Timber.e("Fetch error: ${e.message}")
            null
        } catch (e: IOException) {
            Timber.e("Fetch error local: ${e.message}")
            null
        }
        remotePokemon?.let { pokemon ->
            pokedexDao.deletePokemon(pokemon.id)
            pokedexDao.insertPokemon(pokemon.toPokemonEntity())
        }
    }
}