package com.zjamss.sunny.logic

import android.content.Context
import androidx.lifecycle.liveData
import com.zjamss.sunny.logic.dao.PlaceDao
import com.zjamss.sunny.logic.exception.GlobalException
import com.zjamss.sunny.logic.model.DailyResponse
import com.zjamss.sunny.logic.model.Place
import com.zjamss.sunny.logic.model.Weather
import com.zjamss.sunny.logic.service.SunnyService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import kotlin.coroutines.CoroutineContext


/**
 * @Description: 仓库类
 * @Author: ZJamss
 * @Create: 2022/2/15 21:48
 **/
object Repository {

    //liveData()函数中提供了一个挂起函数的上下文
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeResponse = SunnyService.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(GlobalException.ResponseStatusErrorException(eName = "响应状态:${placeResponse.status}"))
        }
    }

    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async {
                SunnyService.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                SunnyService.getDailyWeather(lng, lat)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()

            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather =
                    Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(GlobalException.ResponseStatusErrorException())
            }
        }

    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavedPlace() = PlaceDao.getSavePlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()
}