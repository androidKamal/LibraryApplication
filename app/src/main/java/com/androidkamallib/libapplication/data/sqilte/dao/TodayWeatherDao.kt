package com.androidkamallib.libapplication.data.sqilte.dao

import androidx.room.Dao
import androidx.room.Query
import com.androidkamallib.libapplication.data.sqilte.entities.TodaysWeather
import com.androidkamallib.library.base.room.BaseDao

@Dao
abstract class TodayWeatherDao : BaseDao<TodaysWeather>() {


    @Query("SELECT * FROM todaysweather WHERE name LIKE :city")
    abstract fun getTodaysWeather(city: String?): TodaysWeather
}