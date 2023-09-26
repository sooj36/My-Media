package com.example.my_media.data

import java.util.Date

data class SubscribeResponse (
    val kind: String = "youtube#subscriptionListResponse",
    val etag: String,
    val nextPageToken: String,
    val pageInfo: PageInfo,
    val items: MutableList<Items>
)

data class PageInfo(
    val totalResults: Int,
    val resultsPerPage: Int
)

data class Items(
    val kind: String = "youtube#subscription",
    val etag: String,
    val id: String,
    val snippet: Snippet
)

data class Snippet(
    val publishedAt: Date,
    val title: String,
    val description: String?,
    val resourceId: ResourceId,
    val channelId: String,
    val thumbnails: Thumbnails
)

data class ResourceId(
    val kind: String ="youtube#channel",
    val channelId: String
)

data class Thumbnails(
    val default: ThumbnailsUrl,
    val medium: ThumbnailsUrl,
    val high: ThumbnailsUrl
)

data class ThumbnailsUrl(
    val url: String
)