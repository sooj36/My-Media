package com.example.my_media.data

import com.example.my_media.BuildConfig

interface YoutubeRepository {
    suspend fun getSubscribe(accessToken: String): SubscribeResponse
    suspend fun getPopularVideo(): PopularVideosResponse
    suspend fun getSearchVideo(): SearchVideoResponse
}

class YoutubeRepositoryImpl(
    private val remote: YoutubeRemoteDataSource
): YoutubeRepository {
    override suspend fun getSubscribe(accessToken: String): SubscribeResponse {
        return remote.getSubscribe(token = accessToken)
    }

    override suspend fun getPopularVideo(): PopularVideosResponse {
        return remote.getPopularVideo(
            part = "snippet",
            chart = "mostPopular",
            regionCode = "KR",
            maxResults = 20,
            key = BuildConfig.API_KEY
        )
    }

    override suspend fun getSearchVideo(): SearchVideoResponse {
        return remote.getSearchVideo(
            part = "snippet",
            chart = "mostPopular",
            maxResults = 10,
            regionCode = "KR",
            apiKey = BuildConfig.API_KEY
        )
    }
}