package com.zjamss.sunny.logic.service


import com.zjamss.sunny.SunnyApplication
import com.zjamss.sunny.logic.model.DailyResponse
import com.zjamss.sunny.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @Program: 天气查询接口
 * @Description:
 * @Author: ZJamss
 * @Create: 2022-02-15 23:20
 **/
interface WeatherService {

    @GET("v2.5/${SunnyApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<RealtimeResponse>

    @GET("v2.5/${SunnyApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<DailyResponse>

}