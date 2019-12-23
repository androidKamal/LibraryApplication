package com.androidkamallib.library.base

import android.app.Application
import android.content.Context
import com.androidkamallib.library.dagger.componant.ApplicationComponent
import com.androidkamallib.library.rx.RxBus

open class BaseApplication : Application() {

    lateinit var bus: RxBus
    lateinit var applicationComponent: ApplicationComponent


    operator fun get(context: Context): BaseApplication {
        return context.applicationContext as BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        bus = RxBus()
    }

    fun bus(): RxBus {
        return bus
    }

}