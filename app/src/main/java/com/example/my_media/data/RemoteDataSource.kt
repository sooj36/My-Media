package com.example.my_media.data

interface DataSource {
    suspend fun getSubscribe(): SubscribeResponse
}
class RemoteDataSource(
    private val service: RetrofitInterface,
    private val accessToken: String
): DataSource {
    override suspend fun getSubscribe(): SubscribeResponse = service.getSubscribe(accessToken)
}