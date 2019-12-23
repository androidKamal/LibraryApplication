package com.androidkamallib.libapplication.util.factory.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androidkamallib.libapplication.view.dashboard.activity.DashBoardActivity
import com.androidkamallib.libapplication.view.dashboard.viewModel.DashboardViewModel

class DashboardViewModelFactory : ViewModelProvider.Factory {
    private var activity: DashBoardActivity

    constructor(activity: DashBoardActivity) {
        this.activity = activity
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DashboardViewModel(
            activity
        ) as (T)
    }
}