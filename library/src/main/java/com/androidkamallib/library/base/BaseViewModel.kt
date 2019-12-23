package com.androidkamallib.library.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


open class BaseViewModel(activity: BaseActivity) : ViewModel() {

    var loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    fun onProgressStart() {
        loadingVisibility.value = true
    }

    fun onProgressFinish() {
        loadingVisibility.value = false

    }
}