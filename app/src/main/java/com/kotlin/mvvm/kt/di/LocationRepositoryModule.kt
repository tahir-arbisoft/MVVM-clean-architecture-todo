package com.kotlin.mvvm.kt.di

import android.content.Context
import com.kotlin.mvvm.kt.data.repository.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocationRepositoryModule {

    @Provides
    fun providesLocationRepository(@ApplicationContext context: Context): LocationRepository {
        return LocationRepository(context)
    }

}





