package com.example.my_media.data

import com.example.my_media.BuildConfig
import com.example.my_media.data.RetrofitClient.retrofit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val api : RetrofitInterface by lazy {
        retrofit.create(RetrofitInterface::class.java)
    }

    private const val BASE_URL = BuildConfig.BASE_URL

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

