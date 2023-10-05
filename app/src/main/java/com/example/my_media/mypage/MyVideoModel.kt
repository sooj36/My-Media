package com.example.my_media.mypage

import com.example.my_media.home.popular.HomePopularModel

data class MyVideoModel(
    val title: String,
    val description: String,
    val photo: String,
    val isLiked: Boolean,
    val viewCount: Long?,
    val likeCount:Long?,
    val commentCount: Long?
)
fun MyVideoModel.toHomePopularModel(): HomePopularModel {
    return HomePopularModel(
        imgThumbnail = photo,
        txtTitle = title,
        txtDescription = description,
        isLiked = isLiked,
        viewCount = viewCount,
        likeCount = likeCount,
        commentCount = commentCount
    )
}
