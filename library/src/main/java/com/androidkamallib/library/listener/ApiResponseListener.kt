package com.androidkamallib.library.listener

import com.androidkamallib.library.base.dto.ResponseDTO


interface ApiResponseListener {
    fun onSuccess(response: ResponseDTO)
    fun onErrorToast(message:String)
    fun showSuccessToast(message:String)
    fun onRetryError(message: String)
}