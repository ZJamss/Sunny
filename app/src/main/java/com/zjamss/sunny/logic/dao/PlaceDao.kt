package com.zjamss.sunny.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.zjamss.sunny.SunnyApplication
import com.zjamss.sunny.logic.model.Place
import kotlin.concurrent.thread

/**
 * @Program: Sunny
 * @Description:
 * @Author: ZJamss
 * @Create: 2022-02-16 01:09
 **/
object PlaceDao {
    fun savePlace(place: Place) {
            sharedPreferences().edit {
                putString("place", Gson().toJson(place))
            }
    }

    fun getSavePlace(): Place {
        val placeJson = sharedPreferences().getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() = SunnyApplication.CONTEXT.
    getSharedPreferences("sunny", Context.MODE_PRIVATE)
}