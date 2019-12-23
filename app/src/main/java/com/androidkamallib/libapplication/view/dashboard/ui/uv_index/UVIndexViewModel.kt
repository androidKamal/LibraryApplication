package com.androidkamallib.libapplication.view.dashboard.ui.uv_index

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UVIndexViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Uv_index Fragment"
    }
    val text: LiveData<String> = _text
}