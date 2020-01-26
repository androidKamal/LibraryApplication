package com.androidkamallib.library.base.dto

import com.google.gson.annotations.SerializedName

open class ResponseDTO {
    @SerializedName("status")
    open var status:Int?=0
    @SerializedName("message")
    open var message:String?=""
}