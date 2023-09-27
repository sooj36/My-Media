package com.example.my_media.data

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//== remote data source
interface RetrofitInterface {

    //확인 필요
    @GET("/v3/videos")
    suspend fun getPopularVideo(
        @Query("part") part : String = "snippet",
        @Query("chart") chart : String = "mostPopluar",
        @Query("regionCode") regionCode : String = "KR",
//        @Query("videoCategoryId") videoCategoryId : String,
        @Query("Key") key: String

    ) : PopularVideosResponse



}