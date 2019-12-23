package com.androidkamallib.libapplication.view.dashboard.ui.forecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SlideshowViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is forecast Fragment"
    }
    val text: LiveData<String> = _text
}