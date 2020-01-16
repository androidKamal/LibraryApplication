package com.androidkamallib.libapplication.app

import com.androidkamallib.libapplication.app.constant.DBConstant
import com.androidkamallib.libapplication.app.constant.SharedPreferenceConstant
import com.androidkamallib.libapplication.app.constant.WebConstant
import com.androidkamallib.libapplication.di.DaggerWeatherApplicationComponent
import com.androidkamallib.libapplication.di.WeatherApplicationComponent
import com.androidkamallib.libapplication.di.appModule.DatabaseModule
import com.androidkamallib.libapplication.di.networkModule.AppNetworkModule
import com.androidkamallib.library.base.BaseApplication
import com.androidkamallib.library.dagger.module.data.preference.SharedPreferencesModule
import com.androidkamallib.library.dagger.module.toast.ToastModule


class MyApplication : BaseApplication() {


    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerWeatherApplicationComponent.builder()
            .sharedPreferencesModule(
                SharedPreferencesModule(
                    this,
                    SharedPreferenceConstant.SHARED_PREFERENCE
                )
            )

            .appNetworkModule(AppNetworkModule(this, WebConstant.BASE_URL))
            .databaseModule(DatabaseModule(this, DBConstant.DBNAme))
            .toastModule(ToastModule(this))
            .build()
        (applicationComponent as WeatherApplicationComponent?)!!.inject(this)

    }


}