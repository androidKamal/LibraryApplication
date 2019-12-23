package com.androidkamallib.libapplication.data.sqilte.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Wind(
    @ColumnInfo(name = "speed")
    val speed: Double? = null,
    @ColumnInfo(name = "deg")
    val deg: Int? = null
)