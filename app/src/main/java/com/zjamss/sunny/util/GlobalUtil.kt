package com.zjamss.sunny.util

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.zjamss.sunny.SunnyApplication

/**
 * @Program: Sunny
 * @Description: 全局简化工具类
 * @Author: ZJamss
 * @Create: 2022-02-15 20:37
 **/
object GlobalUtil {

    fun String.toast(duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(SunnyApplication.CONTEXT, this, duration).show()
    }

    fun Int.toast(duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(SunnyApplication.CONTEXT, this, duration).show()
    }


    fun View.snackbar(
        text: String,
        duration: Int = com.google.android.material.snackbar.Snackbar.LENGTH_SHORT,
        actionText: String? = null,
        block: (() -> Unit)? = null
    ) {
        val snackbar = Snackbar.make(this, text, duration)
        if (actionText != null && block != null) {
            snackbar.setAction(actionText) {
                block()
            }
        }
        snackbar.show()
    }

    fun View.snackbar(
        resId: Int,
        duration: Int = com.google.android.material.snackbar.Snackbar.LENGTH_SHORT,
        actionResId: String? = null,
        block: (() -> Unit)? = null
    ) {
        val snackbar = Snackbar.make(this, resId, duration)
        if (actionResId != null && block != null) {
            snackbar.setAction(actionResId) {
                block()
            }
        }
        snackbar.show()
    }
}