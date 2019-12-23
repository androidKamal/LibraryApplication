package com.androidkamallib.libapplication.data.sqilte.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Main(
    @ColumnInfo(name = "temp")
    val temp: Double? = null,

    @ColumnInfo(name = "feels_like")
    val feels_like: Double? = null,

    @ColumnInfo(name = "temp_min")
    val temp_min: Double? = null,

    @ColumnInfo(name = "temp_max")
    val temp_max: Double? = null,

    @ColumnInfo(name = "pressure")
    val pressure: Int? = null,

    @ColumnInfo(name = "sea_level")
    val sea_level: Int? = null,

    @ColumnInfo(name = "grnd_level")
    val grnd_level: Int? = null,

    @ColumnInfo(name = "humidity")
    val humidity: Int? = null,

    @ColumnInfo(name = "temp_kf")
    val temp_kf: Int? = null
)