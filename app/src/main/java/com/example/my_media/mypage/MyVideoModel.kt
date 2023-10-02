package com.example.my_media.mypage

import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.my_media.home.popular.HomePopularModel

data class MyVideoModel(
    val title: String,
    val description: String,
    val photo: String,
    val isLiked: Boolean,
    var videoCategoryId : String
)
fun MyVideoModel.toHomePopularModel(): HomePopularModel {
    return HomePopularModel(
        imgThumbnail = photo,
        txtTitle = title,
        txtDescription = description,
        isLiked = isLiked,
        videoCategoryId = videoCategoryId
    )
}
