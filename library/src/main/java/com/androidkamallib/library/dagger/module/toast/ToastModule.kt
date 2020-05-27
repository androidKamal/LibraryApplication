package com.androidkamallib.library.dagger.module.toast

import android.R
import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.androidkamallib.library.dagger.annotaions.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
open class ToastModule(
    @ApplicationContext private val context: Context
) {
    @Provides
    @Singleton
    fun provideToast(): Toast {
        val toast = Toast.makeText(context, "message", Toast.LENGTH_SHORT)
        val toastView: View = toast.view
        val textView : TextView= toastView.findViewById(R.id.message)
        textView.setTextColor(Color.WHITE)
        textView.isSingleLine = false
        val params: LinearLayout.LayoutParams = textView.layoutParams as LinearLayout.LayoutParams
        val leftRight = 5f
        val topBottom = 10f
        val newLeftRight = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            leftRight, context.resources.displayMetrics).toInt()
        val newTopBottom = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            topBottom, context.resources.displayMetrics).toInt()
        params.setMargins(newLeftRight,newTopBottom,newLeftRight,newTopBottom)
        textView.setLineSpacing(1.0f, 1.0f);
        textView.layoutParams = params
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,16f)
        return toast
    }
}
