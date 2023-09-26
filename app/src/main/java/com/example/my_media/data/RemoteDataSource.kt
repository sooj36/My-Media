package com.example.my_media.data

interface DataSource {
    suspend fun getSubscribe(): SubscribeResponse
}
class RemoteDataSource(private val service: RetrofitInterface): DataSource {
    override suspend fun getSubscribe(): SubscribeResponse = service.getSubscribe()
}