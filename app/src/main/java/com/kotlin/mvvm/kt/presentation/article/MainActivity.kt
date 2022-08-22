package com.kotlin.mvvm.kt.presentation.article

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.kotlin.mvvm.databinding.ActivityMainBinding
import com.kotlin.mvvm.kt.data.network.ResultData
import com.kotlin.mvvm.kt.domain.models.getArticles.Article
import com.kotlin.mvvm.kt.presentation.article.adapter.ArticleAdapter
import com.kotlin.mvvm.kt.presentation.article.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val articleAdapter = ArticleAdapter()
    private val mainViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        mainViewModel.articleResponse.observe(this, articleObserver)
        mainViewModel.getArticles()
        binding.tv.text = "Hello World 2"
        binding.rvArticleList.adapter = articleAdapter
        binding.btn.setOnClickListener {
            mainViewModel.getLanguage()
        }
        mainViewModel.languageResponse.observe(this, languageObserver)
    }

    private val languageObserver = Observer<ResultData<Article?>>
    { resultData ->
        when (resultData) {
            is ResultData.Loading -> {
                System.out.println("Loading")
                showProgressDialog()
            }
            is ResultData.Success -> {
                resultData.data?.let { article ->
                    onGetArticleResultSuccess(article)
                    System.out.println("Success")
                } ?: run {
                    System.out.println("Parsing issue")
                }

            }
            is ResultData.Logout -> {
                hideProgressDialog()

            }
            is ResultData.Failed -> {
                System.out.println("Failure")
                onGetArticleResultFailure()
                hideProgressDialog()
            }
            is ResultData.Exception -> {
                System.out.println("Exception")
                hideProgressDialog()
            }
        }
    }

    private val articleObserver = Observer<ResultData<Article?>>
    { resultData ->
        when (resultData) {
            is ResultData.Loading -> {
                System.out.println("Loading")
                showProgressDialog()
            }
            is ResultData.Success -> {
                resultData.data?.let { article ->
                    onGetArticleResultSuccess(article)
                    System.out.println("Success")
                } ?: run {
                    System.out.println("Parsing issue")
                }

            }
            is ResultData.Logout -> {
                hideProgressDialog()

            }
            is ResultData.Failed -> {
                System.out.println("Failure")
                onGetArticleResultFailure()
                hideProgressDialog()
            }
            is ResultData.Exception -> {
                System.out.println("Exception")
                hideProgressDialog()
            }
        }
    }

    fun showProgressDialog() {

    }

    fun hideProgressDialog() {

    }

    fun onGetArticleResultSuccess(articles: Article) {
        binding.tv.text = articles.title
        articleAdapter.updateData(articles)
    }

    fun onGetArticleResultFailure() {

    }
}