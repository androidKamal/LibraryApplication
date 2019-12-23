package com.androidkamallib.libapplication.app.constant

class WebConstant {
    companion object {
        val BASE_URL = "http://api.openweathermap.org/data/2.5/"
        var GET_TODAYS_WEATHER = "weather?q={city}&appid=0f66e0a7ca5f84201200d5a7ee9fcb1c"
        val GET_FORECAST = "forecast"
        val OPEN_WEATHER_IMAGE_URL = "http://openweathermap.org/img/wn/"
    }
}