package com.androidkamallib.libapplication.data.sqilte.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "Weather")
data class Weather(
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "main")
    val main: String? = null,
    @ColumnInfo(name = "description")
    val description: String? = null,
    @ColumnInfo(name = "icon")
    val icon: String? = null
)