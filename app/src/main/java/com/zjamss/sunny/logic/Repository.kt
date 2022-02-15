package com.zjamss.sunny.logic

import androidx.lifecycle.liveData
import com.zjamss.sunny.logic.exception.GlobalException
import com.zjamss.sunny.logic.model.Place
import com.zjamss.sunny.logic.service.WeatherService
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import kotlin.concurrent.thread


/**
 * @Description: 仓库类
 * @Author: ZJamss
 * @Create: 2022/2/15 21:48
 **/
object Repository {

    //liveData()函数中提供了一个挂起函数的上下文
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = WeatherService.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(GlobalException.ResponseStatusErrorException(eName = "响应状态:${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
}