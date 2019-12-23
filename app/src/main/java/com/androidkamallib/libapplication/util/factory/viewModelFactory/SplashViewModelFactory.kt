package com.androidkamallib.libapplication.util.factory.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androidkamallib.libapplication.view.splash_activity.activity.SplashActivity
import com.androidkamallib.libapplication.view.splash_activity.viewModel.SplashViewModel

class SplashViewModelFactory : ViewModelProvider.Factory {
    private var activity: SplashActivity

    constructor(activity: SplashActivity) {
        this.activity = activity
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SplashViewModel(
            activity
        ) as (T)
    }
}