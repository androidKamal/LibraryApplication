package com.androidkamallib.libapplication.di


import com.androidkamallib.libapplication.app.MyApplication
import com.androidkamallib.libapplication.di.appModule.DatabaseModule
import com.androidkamallib.libapplication.di.networkModule.AppNetworkModule
import com.androidkamallib.libapplication.view.dashboard.ui.city.viewModel.SelectCityViewModel
import com.androidkamallib.libapplication.view.dashboard.ui.home.viewModel.HomeViewModel
import com.androidkamallib.libapplication.view.dashboard.viewModel.DashboardViewModel
import com.androidkamallib.libapplication.view.splash_activity.activity.SplashActivity
import com.androidkamallib.library.dagger.componant.ApplicationComponent
import com.androidkamallib.library.dagger.module.data.preference.SharedPreferencesModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SharedPreferencesModule::class, AppNetworkModule::class, DatabaseModule::class])
interface WeatherApplicationComponent : ApplicationComponent {


    fun inject(myApplication: MyApplication)
    fun inject(activity: SplashActivity)
    fun inject(viewModel: DashboardViewModel)
    fun inject(viewModel: SelectCityViewModel)
    fun inject(viewModel: HomeViewModel)


    /*val dbHelper: DbHelper*/


}