package com.tomljanovic.matko.pokedex.di

import android.app.Application
import androidx.room.Room
import com.tomljanovic.matko.pokedex.data.local.PokedexDatabase
import com.tomljanovic.matko.pokedex.data.remote.PokeDexApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideRetrofitClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providePokeDexApi(client: OkHttpClient): PokeDexApi {
        return Retrofit.Builder().baseUrl(PokeDexApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()).build().create()
    }

    @Singleton
    @Provides
    fun providePokedexDatabase(app: Application): PokedexDatabase {
        return Room.databaseBuilder(app, PokedexDatabase::class.java, "pokedex.db").build()
    }
}