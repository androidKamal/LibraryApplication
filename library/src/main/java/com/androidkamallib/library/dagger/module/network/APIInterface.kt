package com.androidkamallib.library.dagger.module.network

import retrofit2.Call
import retrofit2.http.*


interface APIInterface {

    @PUT
    fun put(@Url url: String, @Body body: Any): Call<Any>

    @GET
    fun get(@Url url: String, @Header("body") requestDTO: Any): Call<Any>

    @DELETE
    fun delete(@Url url: String, requestDTO: Any): Call<Any>

    @POST
    fun post(@Url url: String, @Body body: Any): Call<Any>

    @POST
    fun post(@Url url: String, @Body body: Any, @Header("body") requestDTO: Any): Call<Any>

    @POST
    fun post(@Url url: String, @Body body: Any, @Header("userName") userName: String, @Header("authorization") authorization: String): Call<Any>
}