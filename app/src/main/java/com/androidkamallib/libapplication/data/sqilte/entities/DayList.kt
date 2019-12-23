package com.androidkamallib.libapplication.data.sqilte.entities

import androidx.room.ColumnInfo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DayList {
    @ColumnInfo(name = "dt")
    @SerializedName("dt")
    @Expose
    private val dt: Int? = null
    @ColumnInfo(name = "main")
    @SerializedName("main")
    @Expose
    private val main: Main? = null
    @ColumnInfo(name = "weather")
    @SerializedName("weather")
    @Expose
    private val weather: List<Weather>? = null
    @ColumnInfo(name = "clouds")
    @SerializedName("clouds")
    @Expose
    private val clouds: Clouds? = null
    @ColumnInfo(name = "wind")
    @SerializedName("wind")
    @Expose
    private val wind: Wind? = null
    @ColumnInfo(name = "SysTemp")
    @SerializedName("SysTemp")
    @Expose
    private val sys: SysTemp? = null
    @ColumnInfo(name = "dt_txt")
    @SerializedName("dt_txt")
    @Expose
    private val dtTxt: String? = null
}