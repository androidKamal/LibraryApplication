package com.androidkamallib.library.dagger.module.data.preference

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPreferencesModule(
    private val context: Context,
    private val sharedPreferenceName: String
) {

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE)
    }
}