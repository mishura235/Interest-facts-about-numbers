package com.example.numbersfact.api

import com.example.numbersfact.api.ApiJSON
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiRequests {
    @GET("{number}?json")
    fun getFact(
        @Path("number") number:String
    ): Call<ApiJSON>

    @GET("random?json")
    fun getRandomFact(): Call<ApiJSON>
}