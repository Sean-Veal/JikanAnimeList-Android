package com.sean.jikananimelist.di

import androidx.compose.runtime.internal.DecoyImplementation
import com.sean.jikananimelist.network.JAnimeClient
import com.sean.jikananimelist.repository.TopRepository
import com.sean.jikananimelist.repository.TopRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideJAnimeClient(): JAnimeClient {
        return JAnimeClient.create()
    }
}