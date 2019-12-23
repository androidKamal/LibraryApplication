package com.androidkamallib.libapplication.view.dashboard.ui.city.dao

import androidx.room.Dao
import com.androidkamallib.libapplication.data.sqilte.entities.TodaysWeather
import com.androidkamallib.library.base.room.BaseDao

@Dao
abstract class TodaysWeatherDAOForSelectCity : BaseDao<TodaysWeather>()