package com.androidkamallib.libapplication.data.sqilte.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class SysTemp(
    @ColumnInfo(name = "pod")
    val pod: String? = null,
    @ColumnInfo(name = "country")
    val country: String? = null,
    @ColumnInfo(name = "sunrise")
    val sunrise: Int? = null,
    @ColumnInfo(name = "sunset")
    val sunset: Int? = null
)