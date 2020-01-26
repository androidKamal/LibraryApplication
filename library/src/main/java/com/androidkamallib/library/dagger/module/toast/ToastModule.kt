package com.androidkamallib.library.dagger.module.toast

import android.content.Context
import android.view.LayoutInflater
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
        return Toast.makeText(context, "message", Toast.LENGTH_SHORT)
    }
}
