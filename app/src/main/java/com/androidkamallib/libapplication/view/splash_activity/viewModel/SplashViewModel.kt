package com.androidkamallib.libapplication.view.splash_activity.viewModel

import android.os.Handler
import com.androidkamallib.libapplication.view.dashboard.activity.DashBoardActivity
import com.androidkamallib.libapplication.view.splash_activity.activity.SplashActivity
import com.androidkamallib.library.base.BaseViewModel


class SplashViewModel : BaseViewModel {
    private var activity: SplashActivity

    constructor(activity: SplashActivity) : super(activity) {
        this.activity = activity
        onProgressStart()

        Handler().postDelayed({
            gotoDashBoard()
        }, 2000)
    }

    fun gotoDashBoard() {
        DashBoardActivity.starter(activity)
    }

}