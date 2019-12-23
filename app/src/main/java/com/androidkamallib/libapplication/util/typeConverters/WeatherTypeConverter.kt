package com.androidkamallib.libapplication.util.typeConverters

import androidx.room.TypeConverter
import com.androidkamallib.libapplication.data.sqilte.entities.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

open class WeatherTypeConverters {


    @TypeConverter
    fun stringToWeather(json: String?): List<Weather>? {
        val gson = Gson()
        val type = object :
            TypeToken<List<Weather?>?>() {}.type
        return gson.fromJson<List<Weather>>(json, type)
    }

    @TypeConverter
    fun weatherToString(list: List<Weather?>?): String? {
        val gson = Gson()
        val type = object :
            TypeToken<List<Weather?>?>() {}.type
        return gson.toJson(list, type)
    }
}
