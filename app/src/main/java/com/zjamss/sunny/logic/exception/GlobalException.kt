package com.zjamss.sunny.logic.exception

import android.view.View
import java.lang.Exception
import java.lang.RuntimeException

/**
* @Description: 全局异常
* @Author: ZJamss
* @Create: 2022/2/15 21:47
**/
sealed class GlobalException(open val eName: String, open val eId: Int) : RuntimeException(eName){
    data class ResponseBodyNullException(override val eName: String = "响应体为空", override val eId: Int = 10404) : GlobalException(eName,eId)
    data class RequestUnsuccessfulException(override val eName: String = "请求失败", override val eId: Int = 10500) : GlobalException(eName,eId)
    data class ResponseStatusErrorException(override val eName: String = "响应状态错误", override val eId: Int = 11500) : GlobalException(eName,eId)
    data class UnKnownException(override var eName: String = "未知错误", override var eId: Int = 10000) : GlobalException(eName,eId)
}


