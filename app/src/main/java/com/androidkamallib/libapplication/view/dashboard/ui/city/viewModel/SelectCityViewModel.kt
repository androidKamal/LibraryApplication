package com.androidkamallib.libapplication.view.dashboard.ui.city.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidkamallib.libapplication.app.MyApplication
import com.androidkamallib.libapplication.app.constant.SharedPreferenceConstant
import com.androidkamallib.libapplication.data.sqilte.WeatherDatabase
import com.androidkamallib.libapplication.data.sqilte.entities.TodaysWeather
import com.androidkamallib.libapplication.di.WeatherApplicationComponent
import com.androidkamallib.libapplication.di.networkModule.MyAPIInterface
import com.androidkamallib.libapplication.view.dashboard.activity.DashBoardActivity
import com.androidkamallib.libapplication.view.dashboard.ui.city.fragemnt.SelectCityFragment
import com.androidkamallib.libapplication.view.dashboard.ui.city.repository.TodaysWeatherRepository
import com.androidkamallib.library.base.BaseActivity
import com.androidkamallib.library.base.BaseFragment
import com.androidkamallib.library.base.BaseViewModel
import com.androidkamallib.library.dagger.module.data.preference.SharedPrefsHelper
import com.androidkamallib.library.dagger.module.toast.ToastHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.text.isNotEmpty as isNotEmpty1

class SelectCityViewModel : BaseViewModel {

    var fragment: SelectCityFragment
    var cityName: MutableLiveData<String> = MutableLiveData()
    var buttonVisibility: MutableLiveData<Boolean> = MutableLiveData()

    @javax.inject.Inject
    lateinit var preferenceHelper: SharedPrefsHelper

    @set:Inject
    var apiInterface: MyAPIInterface? = null

    @javax.inject.Inject
    lateinit var weatherDatabase: WeatherDatabase

    @javax.inject.Inject
    lateinit var toastHelper: ToastHelper

    var todaysWeatherRepository: TodaysWeatherRepository

    constructor(activity: BaseActivity, fragment: BaseFragment) : super(activity) {
        this.fragment = fragment as SelectCityFragment
        this.activity = activity as DashBoardActivity

        (((activity.applicationContext as MyApplication).applicationComponent) as WeatherApplicationComponent).inject(
            this
        )
        todaysWeatherRepository = TodaysWeatherRepository(weatherDatabase.todayWeatherDao()!!)
        onProgressFinish()
        toastHelper.showSuccessToast("Message")
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is city Fragment"
    }
    val text: LiveData<String> = _text


    fun onCityNameTextChanged(text: CharSequence) {
        cityName.value = text.toString()
        buttonVisibility.value = text.isNotEmpty1()
    }

    fun onSearchClick() {
        onProgressStart()
        try {

            val params = HashMap<String, String>()
            params["q"] = cityName.value.toString()
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
                                preferenceHelper.put(
                                    SharedPreferenceConstant.IS_CITY_SELECTED,
                                    true
                                )
                                preferenceHelper.put(
                                    SharedPreferenceConstant.CITY_NAME,
                                    response.body()!!.name!!
                                )
                                activity.onBackPressed()
                            }
                        }
                    } else {
                        if (response.errorBody() == null) {

                        } else {
                            toastHelper.showErrorToast(  response.message())
                        }
                    }
                    onProgressFinish()
                }

            })
        } catch (e: Exception) {
            onProgressFinish()
            toastHelper.showErrorToast( e.toString())
        }


    }
}