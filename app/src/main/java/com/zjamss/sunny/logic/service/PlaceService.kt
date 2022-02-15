package com.zjamss.sunny.logic.service

import com.zjamss.sunny.SunnyApplication
import com.zjamss.sunny.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
* @Description: retrofit接口
* @Author: ZJamss
* @Create: 2022/2/15 21:48
**/

interface PlaceService {
    @GET("v2/place?token=${SunnyApplication.TOKEN}&lang=zh-CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}