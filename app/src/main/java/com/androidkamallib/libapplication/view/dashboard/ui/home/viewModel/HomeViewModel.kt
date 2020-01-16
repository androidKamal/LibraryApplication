package com.androidkamallib.libapplication.view.dashboard.ui.home.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.androidkamallib.libapplication.R
import com.androidkamallib.libapplication.app.MyApplication
import com.androidkamallib.libapplication.app.constant.SharedPreferenceConstant
import com.androidkamallib.libapplication.app.constant.WebConstant
import com.androidkamallib.libapplication.data.sqilte.WeatherDatabase
import com.androidkamallib.libapplication.data.sqilte.entities.TodaysWeather
import com.androidkamallib.libapplication.di.WeatherApplicationComponent
import com.androidkamallib.libapplication.di.networkModule.MyAPIInterface
import com.androidkamallib.libapplication.view.dashboard.activity.DashBoardActivity
import com.androidkamallib.libapplication.view.dashboard.ui.home.fragment.HomeFragment
import com.androidkamallib.libapplication.view.dashboard.ui.homw.repository.HomeTodaysWeatherRepository
import com.androidkamallib.library.base.BaseActivity
import com.androidkamallib.library.base.BaseFragment
import com.androidkamallib.library.base.BaseViewModel
import com.androidkamallib.library.dagger.module.data.preference.SharedPrefsHelper
import com.androidkamallib.library.dagger.module.toast.ToastHelper
import com.androidkamallib.library.util.CalenderUtil.formatToDisplayDate
import com.androidkamallib.library.util.CalenderUtil.formatToIndianTime
import com.androidkamallib.library.util.glide.GlideUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject

class HomeViewModel : BaseViewModel {
    var activity: DashBoardActivity
    var fragment: HomeFragment


    //var currentTime =Date().formatToViewTimeDefaults()
    var toDaysDate: MutableLiveData<String>? = MutableLiveData()
    var toDaysTime: MutableLiveData<String>? = MutableLiveData()
    var todaysWather: MutableLiveData<TodaysWeather>? = MutableLiveData()

    @javax.inject.Inject
    lateinit var preferenceHelper: SharedPrefsHelper

    @set:Inject
    var apiInterface: MyAPIInterface? = null

    @javax.inject.Inject
    lateinit var weatherDatabase: WeatherDatabase

    @javax.inject.Inject
    lateinit var toastHelper: ToastHelper


    var todaysWeatherRepository: HomeTodaysWeatherRepository

    constructor(activity: BaseActivity, fragment: BaseFragment) : super(activity) {
        this.fragment = fragment as HomeFragment
        this.activity = activity as DashBoardActivity
        onProgressStart()
        (((activity.applicationContext as MyApplication).applicationComponent) as WeatherApplicationComponent).inject(
            this
        )
        todaysWeatherRepository = HomeTodaysWeatherRepository(weatherDatabase.todayWeatherDao()!!)

        getTodayWeather()
    }


    fun getTodayWeather() {
        toastHelper.showLongToast( "Called Toast")
        todaysWather!!.value = todaysWeatherRepository.geTodayWeather(
            preferenceHelper.get(
                SharedPreferenceConstant.CITY_NAME,
                ""
            )
        )
        if (todaysWather!!.value == null) {

        } else {
            setTodaysWeather()
        }
    }

    private fun setTodaysWeather() {
        var cal = Calendar.getInstance()
        toDaysTime!!.value =
            cal.formatToIndianTime(((todaysWather!!.value!!.dt).toString() + "000").toLong())
        toDaysDate!!.value =
            cal.formatToDisplayDate(((todaysWather!!.value!!.dt).toString() + "000").toLong())

        activity.getDrawable(
            R.drawable.ic_launcher
        )?.let {
            GlideUtil.loadPicWithPlaceHolder(
                fragment.fragmentSelectCityBinding.ivCloud,
                WebConstant.OPEN_WEATHER_IMAGE_URL + todaysWather!!.value!!.weather!![0].icon!! + "@2x.png",
                activity,
                it
            )
        }
    }

    fun getDate(timeMillisInt: Int): MutableLiveData<String> {
        var cal = Calendar.getInstance()
        var toDaysDate: MutableLiveData<String>? = MutableLiveData()
        toDaysDate!!.value = cal.formatToDisplayDate(((timeMillisInt).toString() + "000").toLong())
        return toDaysDate
    }

    fun getTime(timeMillisInt: Int): MutableLiveData<String> {
        var cal = Calendar.getInstance()
        var toDaysDate: MutableLiveData<String>? = MutableLiveData()
        toDaysDate!!.value = cal.formatToIndianTime(((timeMillisInt).toString() + "000").toLong())
        return toDaysDate
    }


    fun getTodaysWeather() {
        onProgressStart()
        try {

            val params = HashMap<String, String>()
            params["q"] = preferenceHelper.get(
                SharedPreferenceConstant.CITY_NAME,
                ""
            )
            params["appid"] = "0f66e0a7ca5f84201200d5a7ee9fcb1c"
            params["units"] = "metric"
            val getWeather = apiInterface!!.getTodaysWeather(params)
            getWeather.enqueue(object : Callback<TodaysWeather> {
                override fun onFailure(call: Call<TodaysWeather>?, t: Throwable?) {
                    Log.e("Error", call?.toString() + t.toString())
                    onProgressFinish()
                }

                override fun onResponse(
                    call: Call<TodaysWeather>?,
                    response: Response<TodaysWeather>?
                ) {
                    if (response!!.isSuccessful) {
                        if (response.body() == null) {


                        } else {
                            if (todaysWeatherRepository.createTodayWeather(response.body()!!) > 0L) {
                                setTodaysWeather()
                            } else {

                            }
                        }
                    } else {
                        if (response.errorBody() == null) {

                        } else {
                            toastHelper.showLongToast( response.message())
                        }
                    }
                    onProgressFinish()
                }

            })
        } catch (e: Exception) {
            onProgressFinish()
            toastHelper.showLongToast(  e.toString())
            Log.e("Error", e.toString())
        }
    }
}