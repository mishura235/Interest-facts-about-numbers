package com.example.numbersfact.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

const val BASE_URL = "http://numbersapi.com/"

private val retrofit =
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

interface ApiRequests {
    @GET("{number}?json")
    fun getFact(
        @Path("number") number: String
    ): Call<ApiJSON>

    @GET("random?json")
    fun getRandomFact(): Call<ApiJSON>
}

object ApiService {
    val retrofitService: ApiRequests by lazy {
        retrofit.create(ApiRequests::class.java)
    }
}