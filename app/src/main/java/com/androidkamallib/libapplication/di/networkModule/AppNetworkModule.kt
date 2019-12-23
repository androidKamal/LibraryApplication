package com.androidkamallib.libapplication.di.networkModule

import android.content.Context
import com.androidkamallib.library.dagger.module.network.NetworkModule
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class AppNetworkModule(context: Context, baseUrl: String) : NetworkModule(context, baseUrl) {

    @Provides
    @Singleton
    fun getApiInterface(retroFit: Retrofit): MyAPIInterface? {
        return retroFit.create(MyAPIInterface::class.java)
    }
}