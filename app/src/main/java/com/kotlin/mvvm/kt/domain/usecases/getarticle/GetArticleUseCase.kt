package com.kotlin.mvvm.kt.domain.usecases.getarticle

import com.kotlin.mvvm.kt.data.network.ResultData
import com.kotlin.mvvm.kt.data.repository.DataRepository
import com.kotlin.mvvm.kt.domain.models.getArticles.Article
import com.kotlin.mvvm.kt.domain.models.getArticles.GetArticleRequest
import javax.inject.Inject

class GetArticleUseCase @Inject
constructor(private val dataRepository: DataRepository) {

    suspend fun getArticle(apiKey:String): ResultData<Article> {
        try {
            val getArticlesResponse =
                dataRepository.getArticles(apiKey)
            if (getArticlesResponse.errorCode != null) {
                return ResultData.Logout()
            }
            return when (getArticlesResponse.getResponseMessage() == null) {
                true -> {
                    ResultData.Success(getArticlesResponse)
                }
                else -> {
                    ResultData.Failed(message = getArticlesResponse.getResponseMessage())
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return ResultData.Exception()
        }
    }

    suspend fun getLanguage(apiKey:String): ResultData<Article> {
        try {
            val getArticlesResponse =
                dataRepository.getArticles(apiKey)
            if (getArticlesResponse.errorCode != null) {
                return ResultData.Logout()
            }
            return when (getArticlesResponse.getResponseMessage() == null) {
                true -> {
                    ResultData.Success(getArticlesResponse)
                }
                else -> {
                    ResultData.Failed(message = getArticlesResponse.getResponseMessage())
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return ResultData.Exception()
        }
    }
}