package com.androidkamallib.libapplication.view.dashboard.ui.city.repository

import com.androidkamallib.libapplication.data.sqilte.dao.TodayWeatherDao
import com.androidkamallib.libapplication.data.sqilte.entities.TodaysWeather

open class TodaysWeatherRepository {
    var mTodayWeatherDao: TodayWeatherDao? = null

    constructor(mTodayWeatherDao: TodayWeatherDao) {
        this.mTodayWeatherDao = mTodayWeatherDao
    }

    fun createTodayWeather(todaysWeather: TodaysWeather): Long {

        return mTodayWeatherDao!!.insert(todaysWeather)
    }

}