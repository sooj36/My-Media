package com.example.my_media.data

data class Response<T> (
    val kind: String = "youtube#subscriptionListResponse",
    val etga: String,
    val nextPageToken: String,
    val prevPageToken: String,
    val itmes: MutableList<T>
)

data class