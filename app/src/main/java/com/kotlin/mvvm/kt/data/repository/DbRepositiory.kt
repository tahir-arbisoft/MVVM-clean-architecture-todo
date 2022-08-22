package com.kotlin.mvvm.kt.data.repository

import com.kotlin.mvvm.kt.data.Localdb
import javax.inject.Inject

class DbRepositiory @Inject constructor(private val localdb: Localdb) {

    suspend fun getArticles(): String {
        return localdb.getDataFromDb()
    }
}