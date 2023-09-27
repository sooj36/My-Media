package com.example.my_media.data

import com.example.my_media.BuildConfig


interface Repository {
    suspend fun getPopularVideo(): PopularVideosResponse
}

class RepositoryImpl(private val service : RetrofitInterface) : Repository {
    override suspend fun getPopularVideo(): PopularVideosResponse
        = service.getPopularVideo(part = "snippet", chart = "mostPopular", regionCode = "KR", maxResults = 20, key = BuildConfig.API_KEY)
}

//class Repository {
//    suspend fun getPopularVideo() : PopularVideosResponse {
//        return RetrofitClient.api.getPopularVideo(part = "snippet", chart = "mostPopular", regionCode = "KR", maxResults = 20, key = BuildConfig.API_KEY)
//    }
//}