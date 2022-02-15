package com.zjamss.sunny

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.zjamss.sunny.logic.exception.ExceptionHandler
import kotlinx.coroutines.CoroutineExceptionHandler

class SunnyApplication : Application() {
    companion object{

        //彩云天气令牌token
        const val TOKEN = "W4IGrd1ginFLr79I"

        @SuppressLint("StaticFieldLeak")
        lateinit var CONTEXT: Context

    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
    }
}