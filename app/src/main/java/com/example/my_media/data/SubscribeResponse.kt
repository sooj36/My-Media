package com.example.my_media.data

import com.google.gson.annotations.SerializedName
import java.util.Date

data class SubscribeResponse (
    @SerializedName("kind") val kind: String = "youtube#subscriptionListResponse",
    @SerializedName("etag") val eTag: String,
    @SerializedName("nextPageToken") val nextPageToken: String,
    @SerializedName("pageInfo") val pageInfo: PageInfo,
    @SerializedName("items") val items: MutableList<Items>
)

data class PageInfo(
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("resultsPerPage") val resultsPerPage: Int
)

data class Items(
    @SerializedName("kind") val kind: String = "youtube#subscription",
    @SerializedName("etag") val eTag: String,
    @SerializedName("id") val id: String,
    @SerializedName("snippet") val subscribeSnippet: SubscribeSnippet
)

data class SubscribeSnippet(
    @SerializedName("publishedAt") val publishedAt: Date,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String?,
    @SerializedName("resourceId") val resourceId: ResourceId,
    @SerializedName("channelId") val channelId: String,
    @SerializedName("thumbnails") val subscribeThumbnails: SubscribeThumbnails
)

data class ResourceId(
    @SerializedName("kind") val kind: String ="youtube#channel",
    @SerializedName("channelId") val channelId: String
)

data class SubscribeThumbnails(
    @SerializedName("default") val default: ThumbnailsUrl,
    @SerializedName("medium") val medium: ThumbnailsUrl,
    @SerializedName("high") val high: ThumbnailsUrl
)

data class ThumbnailsUrl(
    @SerializedName("url") val url: String
)