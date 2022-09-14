package com.kotlin.mvvm.kt.data.repository

import com.kotlin.mvvm.kt.data.Localdb
import javax.inject.Inject

class DBRepository @Inject constructor(private val localdb: Localdb) {
    suspend fun getCars() = localdb.getDataFromDb()
}