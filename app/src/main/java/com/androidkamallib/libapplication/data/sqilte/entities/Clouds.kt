package com.androidkamallib.libapplication.data.sqilte.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Clouds(
    @ColumnInfo(name = "all")
    var all: Int? = null
)