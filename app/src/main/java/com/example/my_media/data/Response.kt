package com.example.my_media.data

import com.google.gson.annotations.SerializedName

data class Response (
    @SerializedName("items")
    val items: List<YoutubeVideo>
    )

data class YoutubeVideo(
    @SerializedName("id")
    val id: YoutubeID,
    @SerializedName("snippet")
    val snippet: Snippet,
    @SerializedName("contentDetails")
    val contentDetails: ContentDeatails
)

data class YoutubeID(
    @SerializedName("kind")
    val kind: String,
    @SerializedName("channelId")
    val channelId: String
)

data class Snippet(
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("thumbnails")
    val thumbnails: Thumbnails
)

data class Thumbnails(
    @SerializedName("default")
    val default: Thumbnail,
    @SerializedName("medium")
    val medium: Thumbnail,
    @SerializedName("high")
    val high: Thumbnail
)

data class Thumbnail(
    @SerializedName("url")
    val url: String
)
data class ContentDeatails(
    @SerializedName("duration")
    val duration: String
)