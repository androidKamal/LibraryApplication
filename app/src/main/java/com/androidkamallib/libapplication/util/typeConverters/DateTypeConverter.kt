package com.androidkamallib.libapplication.util.typeConverters

import androidx.room.TypeConverter
import com.androidkamallib.library.model.LibDate
import com.androidkamallib.library.util.CalenderUtil.formatToDisplayDate
import com.androidkamallib.library.util.CalenderUtil.formatToIndianTime
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class DateTypeConverter {


    @TypeConverter
    fun stringToDt(json: String?): LibDate? {
        val gson = Gson()
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = (json + "000").toLong()
        var libDate = LibDate()
        libDate.localeTimeStamp = calendar.timeInMillis
        libDate.date = calendar.formatToDisplayDate()
        libDate.time = calendar.formatToIndianTime()

        return libDate
    }

    @TypeConverter
    fun dtToString(list: LibDate?): String? {
        val gson = Gson()
        val type = object :
            TypeToken<LibDate?>() {}.type
        return gson.toJson(list, type)
    }
}