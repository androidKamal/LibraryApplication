package com.androidkamallib.libapplication.view.splash_activity.viewModel

import android.os.Handler
import com.androidkamallib.libapplication.view.dashboard.activity.DashBoardActivity
import com.androidkamallib.libapplication.view.splash_activity.activity.SplashActivity
import com.androidkamallib.library.base.BaseViewModel


class SplashViewModel(activity: SplashActivity) : BaseViewModel(activity) {


    init {
        onProgressStart()
        Handler().postDelayed({
            gotoDashBoard()
        }, 2000)
    }

    private fun gotoDashBoard() {
        DashBoardActivity.starter(activity)
    }

}