package com.kotlin.mvvm.kt.data.network

import com.kotlin.mvvm.kt.domain.models.getArticles.Article
import com.kotlin.mvvm.kt.domain.models.getArticles.GetArticleRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @GET(NetworkingConstants.GET_ARTICLES)
    suspend fun getArticles(
        @Query("api-key") apiKey: String
    ): Article


    @POST(NetworkingConstants.GET_ARTICLES)
    suspend fun getLanguage(
        @Body apiKey: String
    ): Article

}