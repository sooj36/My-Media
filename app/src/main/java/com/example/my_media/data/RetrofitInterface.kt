package com.example.my_media.data

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.Call
interface RetrofitInterface {

    @GET("youtube/v3/search")
    fun video_search(
        @Query("part") part: String,
        @Query("chart") chart: String?,
        @Query("maxResults") maxResults: Int?,
        @Query("regionCode") regionCode: String?,
        @Query("key") apiKey: String
    ): Call<Response>
}