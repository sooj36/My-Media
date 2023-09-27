package com.example.my_media.data

import com.example.my_media.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("/v3/subscriptions")
    suspend fun getSubscribe(
        @Header("Authorization") token: String,
        @Query("part") part: String = "snippet",
        @Query("maxResults") maxResults: Int = 10,
        @Query("mine") subscribe: Boolean = true,
        @Query("order") order: String = "unread",
//        @Query("key") key: String = BuildConfig.API_KEY
    ) : SubscribeResponse
}