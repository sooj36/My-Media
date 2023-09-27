package com.example.my_media.data

import retrofit2.Response


interface DataSource {
    suspend fun getPopularVideo(): PopularVideosResponse
}

//class RemoteDataSource(private val service: RetrofitInterface) : DataSource {
//    override suspend fun getPopularVideo(): PopularVideosResponse
//    = service.getPopularVideo(part = "snippet", chart = "mostPopular", regionCode = "KR", key = "BuildConfig")
//
//}
