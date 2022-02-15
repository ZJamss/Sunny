package com.zjamss.sunny.logic.model

import com.google.gson.annotations.SerializedName

/**
* @Description: 实体类
* @Author: ZJamss
* @Create: 2022/2/15 21:47
**/

data class PlaceResponse(val status: String, val places: List<Place>)

data class Place(
    val name: String,
    val location: Location,
    @SerializedName("formatted_address") val address: String
)

data class Location(val lng: String, val lat: String)