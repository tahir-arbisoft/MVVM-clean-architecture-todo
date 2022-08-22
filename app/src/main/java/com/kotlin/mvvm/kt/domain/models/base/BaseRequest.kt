package com.kotlin.mvvm.kt.domain.models.base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class BaseRequest(
) {
    @SerializedName("appVersion")
    @Expose
    var appVersion: Int

    init {
        appVersion = 1;
    }
}