package com.kotlin.mvvm.kt.di

import android.app.Application
import com.kotlin.mvvm.kt.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbRepositoryModule {

    @Singleton
    @Provides
    fun provideDb(app: Application) = AppDatabase.getInstance(app)

    @Singleton
    @Provides
    fun provideCarDao(db: AppDatabase) = db.getCarListDao()
}