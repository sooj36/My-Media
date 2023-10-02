package com.example.my_media.data

import com.google.gson.annotations.SerializedName

data class SearchVideoResponse(
    @SerializedName("items") val items: List<YoutubeVideo>
)

data class YoutubeVideo(
    @SerializedName("id") val id: YoutubeID?,
    @SerializedName("snippet") val searchSnippet: SearchSnippet?,
    @SerializedName("contentDetails") val contentDetails: ContentDetails?
)

data class YoutubeID(
    @SerializedName("kind") val kind: String?,
    @SerializedName("channelId") val channelId: String?
)

data class SearchSnippet(
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("thumbnails") val searchThumbnails: SearchThumbnails?
)

data class SearchThumbnails(
    @SerializedName("default") val default: SearchThumbnailsUrl?,
    @SerializedName("medium") val medium: SearchThumbnailsUrl?,
    @SerializedName("high") val high: SearchThumbnailsUrl?
)

data class SearchThumbnailsUrl(
    @SerializedName("url") val url: String?
)

data class ContentDetails(
    @SerializedName("duration") val duration: String?
)