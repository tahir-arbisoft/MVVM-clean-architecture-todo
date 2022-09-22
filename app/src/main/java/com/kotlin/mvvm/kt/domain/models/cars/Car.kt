package com.kotlin.mvvm.kt.domain.models.cars

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.sevenpeakssoftware.zain.domain.models.getCars.SubContentItem
import kotlinx.parcelize.Parcelize


/* This class serves both as a Room Entity and API Response POJO
 Can add additional property if need for Room operations*/

@Parcelize
@Entity(tableName = "Car", indices = [Index(value = ["id"], unique = true)])
data class Car(

    @PrimaryKey(autoGenerate = true)
    val pkId: Int = 0,

    @Expose
    @SerializedName("id")
    val id: Int = 0,

    @Expose
    @SerializedName("dateTime")
    val dateTime: String = "",

    @Expose
    @SerializedName("ingress")
    val ingress: String = "",

    @Expose
    @SerializedName("image")
    val image: String = "",

    @Expose
    @SerializedName("created")
    val created: Int = 0,

    @Expose
    @SerializedName("title")
    val title: String = "",

    @Expose
    @SerializedName("content")
    val content: List<SubContentItem> = emptyList(),

    @Expose
    @SerializedName("changed")
    val changed: Int = 0
) : Parcelable