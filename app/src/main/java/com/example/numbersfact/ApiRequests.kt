package com.example.numbersfact

import com.example.numbersfact.api.ApiJSON
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiRequests {
    @GET("{number}?json")
    fun fact(@Path("number") number:String): Call<ApiJSON>

    @GET("random?json")
    fun randomFact(): Call<ApiJSON>

    @GET("random/math?json")
    fun getMath(): Call<ApiJSON>

    @GET("random/date?json")
    fun getDate(): Call<ApiJSON>

}