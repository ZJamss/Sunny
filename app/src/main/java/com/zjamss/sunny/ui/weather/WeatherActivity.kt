package com.zjamss.sunny.ui.weather

import android.content.Context
import android.graphics.Color
import android.inputmethodservice.InputMethodService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zjamss.sunny.R
import com.zjamss.sunny.databinding.ActivityWeatherBinding
import com.zjamss.sunny.logic.model.Weather
import com.zjamss.sunny.logic.model.getSky
import com.zjamss.sunny.util.GlobalUtil.toast
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {

    val viewModel by lazy {ViewModelProvider(this).get(WeatherViewModel::class.java)}
    lateinit var binding: ActivityWeatherBinding
    private lateinit var nowPlaceName :TextView
    private lateinit var currentTemp :TextView
    private lateinit var currentSky :TextView
    private lateinit var currentAQI :TextView
    private lateinit var forecastLayout: LinearLayout
    private lateinit var nowLayout: RelativeLayout
    private lateinit var weatherLayout: ScrollView
    private lateinit var coldRiskText:TextView
    private lateinit var dressingText:TextView
    private lateinit var ultravioletText:TextView
    private lateinit var carWashingText:TextView
    private lateinit var navBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //系统栏透明
        val decorView = window.decorView
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nowPlaceName = findViewById<TextView>(R.id.nowPlaceName)
        currentTemp = findViewById<TextView>(R.id.currentTemp)
        currentSky = findViewById<TextView>(R.id.currentSky)
        currentAQI= findViewById<TextView>(R.id.currentAQI)
        coldRiskText = findViewById<TextView>(R.id.coldRiskText)
        dressingText = findViewById<TextView>(R.id.dressingText)
        ultravioletText = findViewById<TextView>(R.id.ultravioletText)
        carWashingText = findViewById<TextView>(R.id.carWashingText)

        navBtn = findViewById<Button>(R.id.navBtn)

        forecastLayout= findViewById<LinearLayout>(R.id.forecastLayout)
        nowLayout= findViewById<RelativeLayout>(R.id.nowLayout)
        weatherLayout = findViewById<ScrollView>(R.id.weatherLayout)

        if(viewModel.locationLng.isEmpty()) viewModel.locationLng = intent.getStringExtra("location_lng")?:""
        if(viewModel.locationLat.isEmpty()) viewModel.locationLat = intent.getStringExtra("location_lat")?:""
        if(viewModel.placeName.isEmpty()) viewModel.placeName = intent.getStringExtra("place_name")?:""
        viewModel.weatherLiveData.observe(this, Observer {  result ->
            val weather = result.getOrNull()
            if(weather != null){
                showWeatherInto(weather)
            }else {
                "无法成功获取天气".toast()
                result.exceptionOrNull()?.printStackTrace()
            }

        })
        viewModel.refreshWeather(viewModel.locationLng,viewModel.locationLat)

        //下拉刷新天气
        viewModel.weatherLiveData.observe(this, Observer { result->
            val weather = result.getOrNull()
            if(weather != null){
                showWeatherInto(weather)
            }else{
                "无法成功获取天气".toast()
                result.exceptionOrNull()?.printStackTrace()
            }
            binding.swipeRefresh.isRefreshing = false
        })
        binding.swipeRefresh.setColorSchemeResources(R.color.primary)
        refreshWeather()

        //切换城市
        navBtn.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener{
            override fun onDrawerStateChanged(newState: Int) {}
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

            override fun onDrawerOpened(drawerView: View) {}

            override fun onDrawerClosed(drawerView: View) {
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(drawerView.windowToken,InputMethodManager.HIDE_NOT_ALWAYS)
            }
        })
    }

    fun refreshWeather() {
        viewModel.refreshWeather(viewModel.locationLng,viewModel.locationLat)
        binding.swipeRefresh.isRefreshing = true
    }

    private fun showWeatherInto(weather: Weather) {
        findViewById<TextView>(R.id.nowPlaceName).text = viewModel.placeName
        val realtime = weather.realtime
        val daily = weather.daily

        //填充now.xml
        val currentTempText = "${realtime.temperature.toInt()} ℃"
        findViewById<TextView>(R.id.currentTemp).text = currentTempText
        currentSky.text = getSky(realtime.skycon).info
        val currentPM25Text = "空气指数 ${realtime.airQuality.aqi.chn.toInt()}"
        currentAQI.text = currentPM25Text
        nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)

        //填充forecast.xml
        forecastLayout.removeAllViews()
        val days = daily.skycon.size
        for (i in 0 until days){
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]
            val view = LayoutInflater.from(this).inflate(R.layout.forecast_item,forecastLayout,false)
            val dateInfo = view.findViewById<TextView>(R.id.dateInfo)
            val skyIcon = view.findViewById(R.id.skyIcon) as ImageView
            val skyInfo = view.findViewById(R.id.skyInfo) as TextView
            val temperatureInfo = view.findViewById(R.id.temperatureInfo) as TextView
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateInfo.text = simpleDateFormat.format(skycon.date)
            val sky = getSky(skycon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info
            val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} ℃"
            temperatureInfo.text = tempText
            forecastLayout.addView(view)
        }

        //填充life_index.xml布局中的数据
        val lifeIndex = daily.lifeIndex
        coldRiskText.text = lifeIndex.coldRisk[0].desc
        dressingText.text = lifeIndex.dressing[0].desc
        ultravioletText.text = lifeIndex.ultraviolet[0].desc
        carWashingText.text = lifeIndex.carWashing[0].desc
        //让ScrollView变成可见状态
        weatherLayout.visibility = View.VISIBLE
    }
}