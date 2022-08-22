package com.kotlin.mvvm.kt.domain.models.base

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
open class BaseResponse(
    /**
     * @return The responseCode
     */
    /**
     * @param responseCode The responseCode
     */
    @SerializedName("responseCode")
    @Expose
    var responseCode: String? = null,

    //@SerializedName("errorMsg")
    @SerializedName("responseMsg")
    @Expose
    var responseMsg: String? = null,

    @SerializedName("errorCode")
    @Expose
    var errorCode: String? = null,
    @SerializedName("errorTitle")
    @Expose
    var errorTitle: String? = null,
    @SerializedName("errorMsg")
    @Expose
    var errorMsg: String? = null,

    @SerializedName("businessError")
    @Expose
    var businessError: String? = null
) : Parcelable {

    fun getResponseMessage(): String? {
        return if (responseMsg != null) responseMsg else errorMsg
    }
}