package com.androidkamallib.library.dagger.module.toast

import android.R
import android.graphics.Color
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.widget.TextView
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

    fun showErrorToast(message: String, atBottom:Boolean=true, delay:Long = LONG_DELAY){
        val toastView: View = toast.view
        toastView.setBackgroundColor(Color.RED)
        val text: TextView = toastView.findViewById(R.id.message)
        text.setTextColor(Color.WHITE)
        if(atBottom) {
            toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 8)
        }else{
            toast.setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 8)
        }
        toast.setText(message)
        toast.show()
        cancelToast(delay)
    }

    fun showSuccessToast(message: String, atBottom:Boolean=true, delay:Long = LONG_DELAY){
        val toastView: View = toast.view
        toastView.setBackgroundResource(R.color.holo_green_dark)
        val text: TextView = toastView.findViewById(R.id.message)
        if(atBottom) {
            toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 8)
        }else{
            toast.setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 8)
        }
        text.setTextColor(Color.WHITE)
        toast.setText(message)
        toast.show()
        cancelToast(delay)
    }

    private fun cancelToast(long: Long = SHORT_DELAY) {
        Handler().postDelayed({ toast.cancel() }, long)
    }

    fun closeToast(){
       toast.cancel()
    }
}