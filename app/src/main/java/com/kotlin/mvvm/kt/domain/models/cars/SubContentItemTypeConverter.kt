package com.kotlin.mvvm.kt.domain.models.cars

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sevenpeakssoftware.zain.domain.models.getCars.SubContentItem
import java.lang.reflect.Type


class SubContentItemTypeConverter {

    @TypeConverter
    fun fromSubContentItemList(subContentItemList: List<SubContentItem?>?): String? {
        if (subContentItemList == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<SubContentItem?>?>() {}.type
        return gson.toJson(subContentItemList, type)
    }

    @TypeConverter
    fun toSubContentItemList(subContentItemListString: String?): List<SubContentItem>? {
        if (subContentItemListString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<SubContentItem?>?>() {}.type
        return gson.fromJson<List<SubContentItem>>(subContentItemListString, type)
    }
}