package com.androidkamallib.library.util.location

import android.annotation.SuppressLint
import android.app.Activity
import android.content.*
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class FusedLocationTracker : LocationCallback{
    companion object{
        /**
         * Constant used in the location settings dialog.
         */
        const val REQUEST_CHECK_SETTINGS = 0x1
    }
    private val TAG = FusedLocationTracker::class.java.name
    var context: Context
    private var expirationDuration :Long


    private var distance:Float
    var locationListener : LocationListener? = null
    private var priority :Int
    private var clearLastKnowLocation :Boolean
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var mLocationRequest: LocationRequest
    /**
     * get the location.
     */
    private var lastKnownLocation: Location? = null

    /**
     * updateInterval
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    private var updateInterval:Long

    /**fastestUpdateInterval
     * The fastest rate for active location updates. Updates will never be more frequent
     * than this value.
     */
    private var fastestUpdateInterval:Long
    /**
     * Provides access to the Location Settings API.
     */
    private lateinit var mSettingsClient: SettingsClient

    /**
     * Stores the types of location services the client is interested in using. Used for checking
     * settings to determine if the device has optimal location settings.
     */
    private var mLocationSettingsRequest: LocationSettingsRequest? = null

    /**
     * Sets the request  location.
     *
     * @param context : Activity context when you need to show dialog, else Application context
     * @param expirationDuration :
     * @param updateInterval : The desired interval for location updates.
     * @param fastestUpdateInterval :  The fastest rate for active location updates.
     * @param distance : Get location update on distance travelled by device
     * @param locationListener : Get updated location in your activity
     * @param priority : Could be High, Low, Balanced to get location
     * @param clearLastKnowLocation : If user always wants current updated location, clear last know location by providing true
     * @param askToEnableGPS : If GPS is disabled, Show dialog to user to enable GPS
     */
    constructor(context: Context,
                expirationDuration :Long =60 * 60 * 1000,
                updateInterval:Long = 2000,
                fastestUpdateInterval:Long = 1000,
                distance:Float = 20f,
                locationListener : LocationListener? = null,
                priority :Int = LocationRequest.PRIORITY_HIGH_ACCURACY,
                clearLastKnowLocation:Boolean =false,
                askToEnableGPS:Boolean = false){

        Log.d(TAG, "In Fused Location tracker")
        this.context = context
        this.priority=priority
        this.expirationDuration=expirationDuration
        this.updateInterval = updateInterval
        this.fastestUpdateInterval = fastestUpdateInterval
        this.distance = distance
        this.locationListener = locationListener
        this.clearLastKnowLocation=clearLastKnowLocation
        Log.d(TAG, "Call create location request")
        createLocationRequest()
        Log.d(TAG, "Create Fused Location client")

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        Log.d(TAG, "Register GPS state broadcast listener")
        registerGPSStateReceiver()
        if(askToEnableGPS){
            Log.d(TAG, "Ask to enable GPS if GPS is OFF")
            mSettingsClient = LocationServices.getSettingsClient(context);
            buildLocationSettingsRequest()
            enableGPS()
        }else{
            clearLastKnowLocationIfUserWant()
        }

    }


    private fun clearLastKnowLocationIfUserWant(){
        Log.d(TAG, "clea rLast Know Location If User Want and request update location")
        if (clearLastKnowLocation) {
            mFusedLocationClient.flushLocations().addOnCompleteListener {
                requestLocationUpdates()
            }
        }else{
            requestLocationUpdates()
        }
    }



    private fun requestLocationUpdates() {
        Log.d(TAG, "Request update location")
        try {
            mFusedLocationClient.requestLocationUpdates(
                mLocationRequest,
                this, Looper.myLooper()
            )
        } catch (securityException: SecurityException) {
            Log.e(TAG, Objects.requireNonNull(securityException.message)
            )
        }
    }

    /**
     * Sets the request last known location.
     */
    fun requestLastLocation() {
        Log.d(TAG, "Requesting single Location update")
        try {
            mFusedLocationClient.lastLocation
                .addOnCompleteListener { task ->
                    if (task.isSuccessful && task.result != null) {
                        lastKnownLocation = task.result
                        Log.d(TAG,"Received single location point")
                    } else { //Log
                        Log.d(TAG,"Received Zero location point")
                    }
                }
        } catch (securityException: SecurityException) {
            Log.e(TAG, Objects.requireNonNull(securityException.message))
        }
    }

    /**
     * Sets the remove request update.
     */
    fun removeLocationUpdates() {
        Log.d(TAG, "Stopped tracking location")
        try {
            context.unregisterReceiver(mGpsSwitchStateReceiver)
            mFusedLocationClient.removeLocationUpdates(this)
        } catch (securityException: SecurityException) {
            Log.e(TAG, Objects.requireNonNull(securityException.message))
        }
    }

    override fun onLocationResult(locationResult: LocationResult) {
        super.onLocationResult(locationResult)
        Log.d(TAG, "Received new location point")

        lastKnownLocation = locationResult.lastLocation
        locationListener.let {
           it!!.onLocationChanged(lastKnownLocation)
        }
    }

    /**
     * Sets the location request parameters.
     */
    @SuppressLint("RestrictedApi")
    private fun createLocationRequest() {
        Log.d(TAG, "set properties to location request")
        mLocationRequest = LocationRequest()
        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.interval = updateInterval
        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.fastestInterval = fastestUpdateInterval

        mLocationRequest.priority = priority
        // Travel distance
        mLocationRequest.smallestDisplacement = distance
        //mLocationRequest.setExpirationDuration(expirationDuration)

    }

    private fun registerGPSStateReceiver(){
        Log.d(TAG, "Create GPS state broadcast listener")
        val filter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        filter.addAction(Intent.ACTION_PROVIDER_CHANGED)
        context.registerReceiver(mGpsSwitchStateReceiver, filter)
    }

    private val mGpsSwitchStateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action!!.contains(LocationManager.PROVIDERS_CHANGED_ACTION)) {
                Log.d(TAG, "Location provider change listener")
                val locationManager =
                    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val isGpsEnabled =
                    locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                val isNetworkEnabled =
                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                val isPassiveEnabled =
                    locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)
                if(isGpsEnabled || isNetworkEnabled || isPassiveEnabled){
                    Log.d(TAG, "Request location update on provider enabled")
                    requestLocationUpdates()
                }
            }
        }
    }


    /**
     * Uses a [com.google.android.gms.location.LocationSettingsRequest.Builder] to build
     * a [com.google.android.gms.location.LocationSettingsRequest] that is used for checking
     * if a device has the needed location settings.
     */
    private fun buildLocationSettingsRequest() {
        Log.d(TAG, "Build enable GPS if GPS is OFF dialog")
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest)
        mLocationSettingsRequest = builder.build()
    }

    /**
     * Ask GPS to Enable
     *
     */
    private fun enableGPS(){
        Log.d(TAG, "show enable GPS if GPS is OFF dialog")
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest).addOnCompleteListener {
            if(it.isSuccessful){
                clearLastKnowLocationIfUserWant()
            }
        }.addOnFailureListener {
                if(it is ResolvableApiException){
                    try {
                        // Show the dialog by calling startResolutionForResult()
                        if(context is Activity) {
                            it.startResolutionForResult(
                                context as Activity,
                                REQUEST_CHECK_SETTINGS
                            )
                        }
                    } catch (sendEx: IntentSender.SendIntentException) {
                        // Ignore the error.
                    }
                }
            }
    }
}