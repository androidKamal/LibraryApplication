package com.kingmenagent.gpstracker

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.androidkamallib.library.util.CalenderUtil.CalenderPattern
import com.androidkamallib.library.util.CalenderUtil.formatTimeStampToPattern
import com.androidkamallib.library.util.location.FusedLocationTracker
import com.google.android.gms.location.LocationListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), LocationListener {
    lateinit var mFusedLocationTracker: FusedLocationTracker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mFusedLocationTracker = FusedLocationTracker(this)
    }
    var updateCounter=0

    @SuppressLint("SetTextI18n")
    override fun onLocationChanged(location: Location) {
        tvLocation.text ="Accuracy : "+ location.accuracy+",\n" +
                "Bearing: "+location.bearing+",\n" +
                "Altitude: "+location.altitude+", \n" +
                "Date :"+ Calendar.getInstance().formatTimeStampToPattern(location.time,
                                                pattern = CalenderPattern.EEE_MMM_dd_yyyy)+", \n" +
                "Time :"+ Calendar.getInstance().formatTimeStampToPattern(location.time,
                                                pattern = CalenderPattern.hh_mm_a)+ ",\n" +
                "Latitude: "+ location.latitude+", \n" +
                "Longitude: "+location.longitude + ",\n" +
                "Update Counter: "+updateCounter
        updateCounter++
    }

    override fun onDestroy() {
        super.onDestroy()
        mFusedLocationTracker.removeLocationUpdates()
    }
}
