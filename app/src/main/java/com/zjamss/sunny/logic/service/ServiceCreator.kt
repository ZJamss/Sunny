package com.zjamss.sunny.logic.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
* @Description: 创建器
* @Author: ZJamss
* @Create: 2022/2/15 21:48
**/
object ServiceCreator {
    private const val BASE_URL = "https://api.caiyunapp.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass : Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create() : T = create(T::class.java)
}