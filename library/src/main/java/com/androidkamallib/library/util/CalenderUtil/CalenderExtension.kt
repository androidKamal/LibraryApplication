package com.androidkamallib.library.util.CalenderUtil

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


/**
 * Pattern: yyyy-MM-dd HH:mm:ss
 */
private val calendar: Calendar by lazy {
    Calendar.getInstance()
}

fun Date.isSunday(): Boolean {
    calendar.time = this
    return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
}

fun Calendar.formatToServerDateTimeDefaults(locale: Locale = Locale.getDefault()): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale)
    return sdf.format(calendar)
}

fun Calendar.formatToTruncatedDateTime(locale: Locale = Locale.getDefault()): String {
    val sdf = SimpleDateFormat("yyyyMMddHHmmss", locale)
    return sdf.format(this)
}

/**
 * Pattern: yyyy-MM-dd
 */
fun Calendar.formatToServerDateDefaults(locale: Locale = Locale.getDefault()): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", locale)
    return sdf.format(this)
}

/**
 * Pattern: HH:mm:ss
 */
fun Calendar.formatToServerTimeDefaults(locale: Locale = Locale.getDefault()): String {
    val sdf = SimpleDateFormat("HH:mm:ss", locale)
    return sdf.format(calendar.time)
}

/**
 * Pattern: HH:mm A
 */
fun Calendar.formatToIndianTime(
    timeMillis: Long = calendar.timeInMillis,
    locale: Locale = Locale.getDefault()
): String {
    val sdf = SimpleDateFormat("hh:mm a", locale)
    calendar.timeInMillis = timeMillis
    return sdf.format(calendar.time)
}

/**
 * Pattern: EEEEE MMMMM yyyy
 */
fun Calendar.formatToDisplayDate(
    timeMillis: Long = calendar.timeInMillis,
    locale: Locale = Locale.getDefault()
): String {
    val sdf = SimpleDateFormat("EEE, MMM dd, yyyy", locale)
    calendar.timeInMillis = timeMillis
    return sdf.format(calendar.time)
}

/**
 * Pattern: dd/MM/yyyy HH:mm:ss
 */
fun Calendar.formatToViewDateTimeDefaults(locale: Locale = Locale.getDefault()): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", locale)
    return sdf.format(this)
}

/**
 * Pattern: dd/MM/yyyy
 */
fun Calendar.formatToViewDateDefaults(locale: Locale = Locale.getDefault()): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", locale)
    return sdf.format(this)
}

/**
 * Pattern: HH:mm:ss
 */
fun Calendar.formatToViewTimeDefaults(locale: Locale = Locale.getDefault()): String {
    val sdf = SimpleDateFormat("HH:mm:ss", locale)
    return sdf.format(this)
}


/**
 * Pattern: dd/MM/yyyy HH:mm:ss
 */
fun Calendar.getDayOnlyTimeStamp(locale: Locale = Locale.getDefault()): Long {
    calendar.set(Calendar.HOUR, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}


/**
 * Pattern: Calendar from hh:mm a string
 */
fun Calendar.getTimeFromString(locale: Locale = Locale.getDefault(), timeString: String): Long {
    val aFormatter: = SimpleDateFormat("hh:mm a")
    val dt: Date = aFormatter.parse(timeString)
    calendar.time=dt
    return calendar.timeInMillis
}

/**
 * Add field date to current date
 */
fun Date.add(field: Int, amount: Int): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.add(field, amount)

    this.time = cal.time.time

    cal.clear()

    return this
}

