package com.sevenpeakssoftware.zain.domain.models.getCars

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.kotlin.mvvm.kt.domain.models.cars.Car
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetCarResponse(

    @Expose
    @SerializedName("content")
    var content: List<Car>?,

    @Expose
    @SerializedName("status")
    var status: String? = "",

    @Expose
    @SerializedName("serverTime")
    var serverTime: Int = 0

) : Parcelable