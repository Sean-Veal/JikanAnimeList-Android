package com.sean.jikananimelist.di

import com.sean.jikananimelist.repository.TopRepository
import com.sean.jikananimelist.repository.TopRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindingModule {
    @Binds
    @Singleton
    abstract fun bindTopRepository(
        implementation: TopRepositoryImpl
    ): TopRepository
}