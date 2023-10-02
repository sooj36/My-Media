package com.example.my_media.data

import com.google.gson.annotations.SerializedName

data class PopularVideosResponse (
    val items : MutableList<PopularVideosList>,
)

data class PopularVideosList (
    @SerializedName("kind") val kind: String?,
    @SerializedName("etag") val etag: String?,
    @SerializedName("id") val id :  String?,
    @SerializedName("snippet") val popularSnippet : PopularSnippet?,
    @SerializedName("channelTitle") val channelTitle : String?,
    @SerializedName("detaultLanguage") val defaultLanguage : String?,
    @SerializedName("categoryId") val categoryId :  Int?,
)

data class PopularSnippet (
    @SerializedName("publishedAt") val publishedAt : String?,
    @SerializedName("channelId") val channelId : String?,
    @SerializedName("title") val title : String?,
    @SerializedName("description") val description : String?,
    @SerializedName("thumbnails") val popularThumbnails : PopularThumbnails?,
    @SerializedName("standard") val standard : String?,
)

// 썸네일
data class PopularThumbnails (
    @SerializedName("standard") val standard: Standard?
)

// 화질
data class Standard (
    @SerializedName("url") val url : String?,
    @SerializedName("width") val width: Int?,
    @SerializedName("height") val height : Int?
)

