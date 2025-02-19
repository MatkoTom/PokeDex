package com.tomljanovic.matko.pokedex.di

import com.tomljanovic.matko.pokedex.data.repository.PokeDexRepositoryImpl
import com.tomljanovic.matko.pokedex.domain.repository.PokeDexRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun providePokeDexRepository(pokeDexRepositoryImpl: PokeDexRepositoryImpl): PokeDexRepository
}