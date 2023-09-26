package com.example.my_media.data

import android.icu.text.ListFormatter.Width

data class PopularVideosResponse (
    val items : MutableList<PopularVideosList>,
)

data class PopularVideosList (
    val kind: String,
    val etag: String,
    val id :  String,
    val snippet : String,
    val channelTitle : String,
    val defaultLanguage : String,
    val categoryId :  Int,
)

data class snippet (
    val publishedAt : String,
    val channelId : String,
    val title : String,
    val description : String,
    val thumbnails :  String,
    val standard : String,
)

// 썸네일
data class thumbnails (
    val standard: String
)

// 화질
data class standard (
    val url : String,
    val width: Int,
    val height : Int
)

