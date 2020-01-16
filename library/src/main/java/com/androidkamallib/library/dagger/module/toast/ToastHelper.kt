package com.androidkamallib.library.dagger.module.toast

import android.os.Handler
import android.widget.Toast
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
open class ToastHelper
@Inject
constructor(private val toast: Toast) {

    private val LONG_DELAY = 3500L // 3.5 seconds

    private val TOO_LONG_DELAY = 5000L // 3.5 seconds

    private val SHORT_DELAY = 2000L // 2 seconds

    private val TOO_SHORT_DELAY = 1000L // 2 seconds

    fun showShortToast(message: String) {
        toast.setText(message)
        toast.show()
        cancelToast()

    }

    fun showTooShortToast(message: String) {
        toast.setText(message)
        toast.show()
        cancelToast(TOO_SHORT_DELAY)
    }

    fun showTooLongToast(message: String) {
        toast.setText(message)
        toast.show()
        cancelToast(TOO_LONG_DELAY)
    }

    fun showLongToast(message: String) {
        toast.setText(message)
        toast.show()
        cancelToast(LONG_DELAY)
    }

    private fun cancelToast(long: Long = SHORT_DELAY) {
        Handler().postDelayed({ toast.cancel() }, long)
    }
}