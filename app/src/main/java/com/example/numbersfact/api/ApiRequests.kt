package com.example.numbersfact.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiRequests {
    @GET("{number}?json")
    fun fact(@Path("number") number:String): Call<ApiJSON>

    @GET("random?json")
    fun randomFact(): Call<ApiJSON>
}