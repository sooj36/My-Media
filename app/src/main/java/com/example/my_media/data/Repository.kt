package com.example.my_media.data

import com.example.my_media.BuildConfig


interface Repository {

    suspend fun getPopularVideo(): PopularVideosResponse

}

class RepositoryImpl(private val service : RetrofitInterface) : Repository {
    override suspend fun getPopularVideo(): PopularVideosResponse
        = service.getPopularVideo(part = "snippet", chart = "mostPopular", regionCode = "KR", key = BuildConfig.API_KEY)
}