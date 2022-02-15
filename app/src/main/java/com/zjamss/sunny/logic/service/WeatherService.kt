package com.zjamss.sunny.logic.service

import com.zjamss.sunny.logic.exception.GlobalException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @Description: 天气服务
 * @Author: ZJamss
 * @Create: 2022/2/15 21:48
 **/
object WeatherService {

    private val placeService = ServiceCreator.create<PlaceService>()

    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) {
                        continuation.resume(body)
                    } else {
                        continuation.resumeWithException(GlobalException.ResponseBodyNullException())
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    t.printStackTrace()
                    continuation.resumeWithException(GlobalException.RequestUnsuccessfulException())
                }
            })
        }
    }
}