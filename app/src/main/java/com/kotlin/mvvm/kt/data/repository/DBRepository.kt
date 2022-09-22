package com.kotlin.mvvm.kt.data.repository

import com.kotlin.mvvm.kt.data.AppDatabase
import javax.inject.Inject

class DBRepository @Inject constructor(private val localdb: AppDatabase) {
    suspend fun getCars() = localdb.getCarListDao().getCars()
}