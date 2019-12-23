package com.androidkamallib.libapplication.view.dashboard.ui.homw.repository

import com.androidkamallib.libapplication.data.sqilte.dao.TodayWeatherDao
import com.androidkamallib.libapplication.data.sqilte.entities.TodaysWeather
import com.androidkamallib.libapplication.view.dashboard.ui.city.repository.TodaysWeatherRepository

class HomeTodaysWeatherRepository(mTodayWeatherDao: TodayWeatherDao) :
    TodaysWeatherRepository(mTodayWeatherDao) {

    fun geTodayWeather(cityName: String): TodaysWeather {

        return mTodayWeatherDao!!.getTodaysWeather(cityName)
    }
}