package com.sevenpeakssoftware.zain.domain.models.getCars

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubContentItem(
    @Expose
    @SerializedName("type")
    val type: String = "",

    @Expose
    @SerializedName("subject")
    val subject: String = "",

    @Expose
    @SerializedName("description")
    val description: String = "",
) : Parcelable