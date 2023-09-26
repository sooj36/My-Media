package com.example.my_media.data

interface Repository {
    suspend fun getSubscribe(): SubscribeResponse
}

class RepositoryImpl(
    private val remote: RemoteDataSource
): Repository {
    override suspend fun getSubscribe(): SubscribeResponse {
        return remote.getSubscribe()
    }
}