package com.zjamss.sunny

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.zjamss.sunny.logic.exception.GlobalException
import com.zjamss.sunny.util.GlobalUtil.snackbar
import java.lang.RuntimeException
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}