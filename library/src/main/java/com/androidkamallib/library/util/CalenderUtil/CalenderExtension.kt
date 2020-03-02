package com.androidkamallib.library.util.CalenderUtil

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class CalenderPattern(){
    companion object{
        var yyyy_MM_dd_HH_mm_ss ="yyyy-MM-dd HH:mm:ss"
        var yyyyMMddHHmmss = "yyyyMMddHHmmss"
        var yyyy_MM_dd = "yyyy-MM-dd"
        var HH__mm__ss = "HH:mm:ss"
        var dd_MM_yyyy_HH__mm__ss = "dd/MM/yyyy HH:mm:ss"
        var dd_MM_yyyy = "dd/MM/yyyy"
        var hh_mm_a = "hh:mm a"
        var EEE_MMM_dd_yyyy = "EEE, MMM dd, yyyy"
        var dd_MM_yyyy_hh_mm_a = "dd:MM:yyyy hh:mm a"
        var DD_MMM_YYYY_hh_mm_a = "DD-MMM-YYYY hh:mm a"
    }
}

private val calendar: Calendar by lazy {
    Calendar.getInstance()
}

fun Date.isSunday(): Boolean {
    calendar.time = this
    return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
}

fun Calendar.formatToPatternDateTime(locale: Locale  = Locale.ENGLISH, pattern : String): String {
    val sdf = SimpleDateFormat(pattern, locale)
    return sdf.format(calendar)
}

fun Calendar.formatTimeStampToPattern(
    timeMillis: Long = calendar.timeInMillis,
    locale: Locale  = Locale.ENGLISH,
    pattern: String
): String {
    val sdf = SimpleDateFormat(pattern, locale)
    calendar.timeInMillis = timeMillis
    return sdf.format(calendar.time)
}

/**
 * get Today's time stamp
 */
fun Calendar.getDayOnlyTimeStamp(): Long {
    calendar.set(Calendar.HOUR, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}


/**
 * Pattern: Calendar from hh:mm a string
 */
fun Calendar.getTimeFromString(locale: Locale = Locale.ENGLISH, dateTimeString: String, pattern: String): Long {
    val aFormatter = SimpleDateFormat(pattern,locale)
    val dt = aFormatter.parse(dateTimeString)
    calendar.time=dt
    return calendar.timeInMillis
}


/**
 * Add Day/Month/Year date to current date
 */
fun Date.add(field: Int, amount: Int): Date {
    val cal = calendar
    cal.time = this
    cal.add(field, amount)
    this.time = cal.time.time
    cal.clear()
    return this
}



