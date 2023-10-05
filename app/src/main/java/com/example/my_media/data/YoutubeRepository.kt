package com.example.my_media.data

import com.example.my_media.BuildConfig

interface YoutubeRepository {
    suspend fun getSubscribe(accessToken: String): SubscribeResponse
    suspend fun getPopularVideo(accessToken: String, videoCategoryId: String): PopularVideosResponse
    suspend fun getSearchVideo(query: String): SearchVideoResponse
}

class YoutubeRepositoryImpl(
    private val remote: YoutubeRemoteDataSource
): YoutubeRepository {
    override suspend fun getSubscribe(accessToken: String): SubscribeResponse {
        return remote.getSubscribe(token = accessToken)
    }

    override suspend fun getPopularVideo(accessToken: String, videoCategoryId: String): PopularVideosResponse {
        return remote.getPopularVideo(
            token = accessToken,
            videoCategoryId = videoCategoryId
        )
    }

    override suspend fun getSearchVideo(query: String): SearchVideoResponse {
        return remote.getSearchVideo(
            part = "snippet",
            query = query,
            maxResults = 10,
            regionCode = "KR",
            apiKey = BuildConfig.API_KEY
        )
    }
}