package com.kotlin.mvvm.kt.data.repository
import com.kotlin.mvvm.kt.data.network.ApiService
import com.kotlin.mvvm.kt.domain.models.getArticles.Article
import com.kotlin.mvvm.kt.domain.models.getArticles.GetArticleRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class DataRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getArticles(apiKey: String): Article {
        return apiService.getArticles(apiKey)
    }

    suspend fun getLanguage(apiKey: String): Article {
        return apiService.getLanguage(apiKey)
    }
}
