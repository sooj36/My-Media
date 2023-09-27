package com.example.my_media.data

import com.example.my_media.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = BuildConfig.BASE_URL

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val service: YoutubeRemoteDataSource by lazy {
        retrofit.create(YoutubeRemoteDataSource::class.java)
    }
}