package com.androidkamallib.libapplication.data.sqilte.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CityByDate")
data class CityByDate(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @Embedded var coord: Coordinate? = null,
    @ColumnInfo(name = "country")
    var country: String? = null,
    @ColumnInfo(name = "timezone")
    var timezone: Int? = null,
    @ColumnInfo(name = "sunrise")
    var sunrise: Int? = null,
    @ColumnInfo(name = "sunset")
    var sunset: Int? = null

)