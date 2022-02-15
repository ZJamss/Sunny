package com.zjamss.sunny.logic.model

import com.google.gson.annotations.SerializedName

/**
* @Description: 当日天气
* @Author: ZJamss
* @Create: 2022/2/15 23:17
**/
data class RealtimeResponse(val status:String,val result:Result){

    data class Result(val realtime:Realtime)

    data class Realtime(val skycon:String,val temperature:Float,@SerializedName("air_quality") val airQuality:AirQuality)

    data class AirQuality(val aqi:AQI)

    data class AQI(val chn:Float)
}
