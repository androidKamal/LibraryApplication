package com.androidkamallib.libapplication.view.dashboard.viewModel

import com.androidkamallib.libapplication.R
import com.androidkamallib.libapplication.app.MyApplication
import com.androidkamallib.libapplication.app.constant.SharedPreferenceConstant
import com.androidkamallib.libapplication.di.WeatherApplicationComponent
import com.androidkamallib.libapplication.view.dashboard.activity.DashBoardActivity
import com.androidkamallib.library.base.BaseViewModel
import com.androidkamallib.library.dagger.module.data.preference.SharedPrefsHelper

class DashboardViewModel : BaseViewModel {


    @javax.inject.Inject
    lateinit var preferenceHelper: SharedPrefsHelper

    constructor(activity: DashBoardActivity) : super(activity) {
        this.activity = activity
        (((activity.applicationContext as MyApplication).applicationComponent) as WeatherApplicationComponent).inject(
            this
        )

    }


    fun checkIfCitySelected(): Boolean {
        return if ((preferenceHelper[SharedPreferenceConstant.IS_FIRST_TIME, true] && !preferenceHelper.get(
                SharedPreferenceConstant.IS_CITY_SELECTED,
                false
            ))
        ) {
            (activity as DashBoardActivity).navView.menu.performIdentifierAction(R.id.nav_select_city, 0)
            false
        } else {
            true
        }

    }

}