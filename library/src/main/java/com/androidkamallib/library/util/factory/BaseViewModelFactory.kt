package com.androidkamallib.library.util.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androidkamallib.library.base.BaseActivity
import com.androidkamallib.library.base.BaseViewModel

@Suppress("UNCHECKED_CAST")
class BaseViewModelFactory : ViewModelProvider.Factory {
    private var activity: BaseActivity

    constructor(activity: BaseActivity) {
        this.activity = activity
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BaseViewModel(
            activity
        ) as (T)
    }
}