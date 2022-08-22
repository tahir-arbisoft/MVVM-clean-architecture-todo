package com.kotlin.mvvm.kt.presentation.article.viewmodel

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kotlin.mvvm.kt.data.network.ResultData
import com.kotlin.mvvm.kt.domain.models.getArticles.Article
import com.kotlin.mvvm.kt.domain.models.getArticles.GetArticleRequest
import com.kotlin.mvvm.kt.domain.usecases.getarticle.GetArticleUseCase
import com.kotlin.mvvm.kt.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val useCase: GetArticleUseCase) : BaseViewModel() {

    private var mutableArticleOutPut = MutableLiveData<ResultData<Article?>>()
    val articleResponse: LiveData<ResultData<Article?>>
        get() = mutableArticleOutPut

    private var mutableLanguageOutPut = MutableLiveData<ResultData<Article?>>()
    val languageResponse: LiveData<ResultData<Article?>>
        get() = mutableLanguageOutPut


    fun getArticles() {
        mutableArticleOutPut.value = ResultData.Loading()

        viewModelScope.launch {
            val result = useCase.getArticle("XycaY5jVXLA3HnQFE5e2IdR8sMJ6XSqM")
            mutableArticleOutPut.value = result
        }

    }

    fun getLanguage() {
        mutableLanguageOutPut.value = ResultData.Loading()

        viewModelScope.launch {
            val result = useCase.getArticle("XycaY5jVXLA3HnQFE5e2IdR8sMJ6XSqM")
            mutableLanguageOutPut.value = result
        }

    }



}