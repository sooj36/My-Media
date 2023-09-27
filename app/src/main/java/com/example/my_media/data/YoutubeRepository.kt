package com.example.my_media.data

interface YoutubeRepository {
    suspend fun getSubscribe(accessToken: String): SubscribeResponse
}

class YoutubeRepositoryImpl(
    private val remote: YoutubeRemoteDataSource
): YoutubeRepository {
    override suspend fun getSubscribe(accessToken: String): SubscribeResponse {
        return remote.getSubscribe(token = accessToken)
    }
}