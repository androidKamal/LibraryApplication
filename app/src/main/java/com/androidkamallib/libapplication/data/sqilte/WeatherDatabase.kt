package com.androidkamallib.libapplication.data.sqilte

import androidx.room.Database
import androidx.room.RoomDatabase
import com.androidkamallib.libapplication.data.sqilte.dao.TodayWeatherDao
import com.androidkamallib.libapplication.data.sqilte.entities.CityByDate
import com.androidkamallib.libapplication.data.sqilte.entities.TodaysWeather


@Database(entities = [TodaysWeather::class, CityByDate::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun todayWeatherDao(): TodayWeatherDao?
}