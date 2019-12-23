package com.androidkamallib.libapplication.data.sqilte.entities

import androidx.room.*
import com.androidkamallib.libapplication.util.typeConverters.WeatherTypeConverters


@Entity(tableName = "TodaysWeather")
@TypeConverters(WeatherTypeConverters::class)
data class TodaysWeather(

    //@ColumnInfo(name = "coord")
    @Embedded
    var coord: Coordinate? = null,

    var weather: List<Weather>? = null,

    @ColumnInfo(name = "base")
    var base: String? = null,

    //@ColumnInfo(name = "main")
    @Embedded
    var main: Main? = null,
    //@ColumnInfo(name = "wind")
    @Embedded
    var wind: Wind? = null,

    //@ColumnInfo(name = "clouds")
    @Embedded
    var clouds: Clouds? = null,

    @ColumnInfo(name = "dt")
    var dt: Int? = null,

    //@ColumnInfo(name = "sys")
    @Embedded
    var sys: SysTemp? = null,

    @ColumnInfo(name = "timezone")
    var timezone: Int? = null,

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "cod")
    var cod: Int? = null
)