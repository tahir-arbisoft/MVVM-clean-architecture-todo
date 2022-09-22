package com.kotlin.mvvm.kt.di

import com.kotlin.mvvm.kt.data.dao.CarDAO
import com.kotlin.mvvm.kt.data.network.ApiService
import com.kotlin.mvvm.kt.data.repository.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataRepositoryModule {

    @Provides
    fun provideDataRepository(apiService: ApiService, carDao: CarDAO): DataRepository {
        return DataRepository(apiService, carDao)
    }
}