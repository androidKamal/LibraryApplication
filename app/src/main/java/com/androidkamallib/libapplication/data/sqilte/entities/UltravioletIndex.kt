package com.androidkamallib.libapplication.data.sqilte.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "city")
class UltravioletIndex {
    @ColumnInfo(name = "lat")
    var lattitude: Double? = null
    @ColumnInfo(name = "lon")
    var longitude: Double? = null
    @ColumnInfo(name = "date_iso")
    var dateIso: String? = null
    @ColumnInfo(name = "date")
    var date: Long? = null
    @ColumnInfo(name = "value")
    var value: Double? = null
}