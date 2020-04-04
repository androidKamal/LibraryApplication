package com.androidkamallib.library.util.common

import java.lang.StringBuilder
import java.util.*

class UniqueNumber {
    companion object{

         fun getRandomString(length: Int = 20, initial: String = ""): String {
            val alphaNumericString = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                    + "0123456789"
                    + "abcdefghijklmnopqrstuvxyz")
            val sb = StringBuilder(length)

            for (i in 0 until length) { // generate a random number between
// 0 to AlphaNumericString variable length
                val index = (alphaNumericString.length
                        * Math.random()).toInt()
                // add Character one by one in end of sb
                sb.append(
                    alphaNumericString[index]
                )
            }

            return (initial + sb + Calendar.getInstance().timeInMillis).replace("-", "")
        }
    }
}