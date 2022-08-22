package com.kotlin.mvvm.kt.presentation.article.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.mvvm.databinding.ArticleListItemViewBinding
import com.kotlin.mvvm.kt.domain.models.getArticles.Article

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.Companion.ArticleViewHolder>() {

    private val dataList = mutableListOf<Article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            ArticleListItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    fun updateData(newData: List<Article>) {
        val diffUtil = ArticleDiffUtil(dataList, newData)
        val difference = DiffUtil.calculateDiff(diffUtil)

        dataList.clear()
        dataList.addAll(newData)
        difference.dispatchUpdatesTo(this)
    }

    companion object {
        class ArticleDiffUtil(
            private val oldList: List<Article>,
            private val newList: List<Article>,
        ) : DiffUtil.Callback() {
            override fun getOldListSize(): Int = oldList.size

            override fun getNewListSize(): Int = newList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].id == newList[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }

        }

        class ArticleViewHolder(
            private val itemBinding: ArticleListItemViewBinding
        ) : RecyclerView.ViewHolder(itemBinding.root) {
            fun bind(article: Article) {
                itemBinding.title.text = article.title
            }
        }
    }
}