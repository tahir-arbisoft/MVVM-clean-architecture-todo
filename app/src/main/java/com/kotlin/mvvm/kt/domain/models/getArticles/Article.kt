package com.kotlin.mvvm.kt.domain.models.getArticles

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.kotlin.mvvm.kt.domain.models.base.BaseResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    @Expose
    @SerializedName("id")
    val id: Long? = 0,

    @Expose
    @SerializedName("published_date")
    val published_date: String? = null,

    @Expose
    @SerializedName("title")
    val title: String? = null,

    @Expose
    @SerializedName("abstract")
    val abstractArticle: String? = null,

    @Expose
    @SerializedName("media")
    val media: List<Media>? = ArrayList(),
) : BaseResponse(), Parcelable