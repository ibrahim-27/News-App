package com.example.newsapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

/** API interface **/
interface APIinterface {
    @GET
    fun GetData(@Url url:String): Call<News>
}