package com.androidkamallib.libapplication.util.factory.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androidkamallib.libapplication.view.dashboard.ui.home.viewModel.HomeViewModel
import com.androidkamallib.library.base.BaseActivity
import com.androidkamallib.library.base.BaseFragment

class HomeViewModelFactory : ViewModelProvider.Factory {
    private var activity: BaseActivity
    private var baseFragment: BaseFragment

    constructor(activity: BaseActivity, baseFragment: BaseFragment) {
        this.activity = activity
        this.baseFragment = baseFragment
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(
            activity, baseFragment
        ) as (T)
    }
}