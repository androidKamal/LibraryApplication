package com.androidkamallib.library.util.common

import java.lang.StringBuilder
import java.util.*

class UniqueNumber {
    companion object{
        fun getRandomString(lenght:Int=40, initial:String=""):String{
            val alphaNumericString = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                    + "0123456789"
                    + "abcdefghijklmnopqrstuvxyz")
            val sb = StringBuilder(lenght-25)

            for (i in 0 until lenght-25) { // generate a random number between
// 0 to AlphaNumericString variable length
                val index = (alphaNumericString.length
                        * Math.random()).toInt()
                // add Character one by one in end of sb
                sb.append(
                    alphaNumericString[index]
                )
            }

            return (initial+sb+UUID.randomUUID().toString()+Calendar.getInstance().timeInMillis).replace("-","")
        }
    }
}