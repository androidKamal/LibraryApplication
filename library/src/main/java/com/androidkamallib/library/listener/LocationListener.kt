package com.androidkamallib.library.listener

import android.location.Location

interface LocationListener {
    fun onLocationChange(location:Location)
}