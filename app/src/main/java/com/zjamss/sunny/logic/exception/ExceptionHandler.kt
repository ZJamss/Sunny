package com.zjamss.sunny.logic.exception

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.widget.Toast
import com.zjamss.sunny.util.GlobalUtil.snackbar
import com.zjamss.sunny.util.GlobalUtil.toast
import kotlin.concurrent.thread
import kotlin.system.exitProcess

/**
 * @Description: 全局异常拦截器
 * @Author: ZJamss
 * @Create: 2022/2/15 20:31
 **/
class ExceptionHandler : Thread.UncaughtExceptionHandler {

    // 单例模式
    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: ExceptionHandler? = null
        fun getInstance(): ExceptionHandler? {
            if (instance == null) {
                synchronized(ExceptionHandler::class) {
                    instance = ExceptionHandler()
                }
            }
            return instance
        }
    }

    /**
     * 构造初始化
     */
    init {
        // 将此类设置为当前线程或线程组的未捕获异常拦截器
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    /**
     * 当 UncaughtException 发生时转入该函数
     * @param t
     * @param e
     */
    override fun uncaughtException(t: Thread, e: Throwable) {
        handleException(
            when (e) {
                is GlobalException.ResponseBodyNullException -> e
                else -> e as GlobalException.UnKnownException
            }
        )
    }

    /**
     * 自定义错误处理
     * @param e
     */
    private fun handleException(e: GlobalException) {
        thread {
            Looper.prepare()
            "${e.eName}(${e.eId})".toast()
            Looper.loop()
        }
    }
}
