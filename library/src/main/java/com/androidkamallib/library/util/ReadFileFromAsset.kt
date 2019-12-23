package com.androidkamallib.library.util

import android.app.Application

class ReadFileFromAsset {
    companion object {
        fun readFileToJson(file_name: String, application: Application): String {

            val json_string = application.assets.open(file_name).bufferedReader().use {
                it.readText()
            }
            return json_string
        }
    }
}