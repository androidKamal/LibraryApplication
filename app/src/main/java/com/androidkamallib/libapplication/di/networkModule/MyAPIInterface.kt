package com.androidkamallib.libapplication.di.networkModule

import com.androidkamallib.libapplication.data.sqilte.entities.TodaysWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap

interface MyAPIInterface {

    @Headers("Content-Type:application/json; charset=UTF-8")
    @GET("weather")
    fun getTodaysWeather(@QueryMap params: Map<String, String>): Call<TodaysWeather>
}