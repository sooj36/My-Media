package com.example.my_media.home.popular

import android.os.Parcelable
import com.example.my_media.mypage.MyVideoModel
import kotlinx.android.parcel.Parcelize
@Parcelize
data class HomePopularModel(
    val imgThumbnail: String,
    val txtTitle: String,
    val txtDescription : String,
    var isLiked: Boolean,
    var viewCount: Long?,
    var likeCount:Long?,
    val commentCount: Long?
): Parcelable
fun HomePopularModel.toMyVideoModel():MyVideoModel{
    return MyVideoModel(
        title = txtTitle,
        description = txtDescription,
        photo = imgThumbnail,
        isLiked = isLiked,
        viewCount = viewCount,
        likeCount = likeCount,
        commentCount = commentCount

    )
}