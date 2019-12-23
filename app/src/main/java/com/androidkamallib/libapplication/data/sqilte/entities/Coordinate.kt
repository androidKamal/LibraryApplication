package com.androidkamallib.libapplication.data.sqilte.entities

import androidx.room.ColumnInfo

data class Coordinate(
    @ColumnInfo(name = "lon")
    var longitude: Long? = null,
    @ColumnInfo(name = "lat")
    var lattitude: Long? = null

)