package com.example.my_media.home.popular

import android.os.Parcelable
import com.example.my_media.mypage.MyVideoModel
import kotlinx.android.parcel.Parcelize
import java.util.Date
@Parcelize
data class HomePopularModel(
    val imgThumbnail: String,
    val txtTitle: String,
    val txtDescription : String,
    var isLiked : Boolean,
    var videoCategoryId : String
): Parcelable
fun HomePopularModel.toMyVideoModel():MyVideoModel{
    return MyVideoModel(
        title = txtTitle,
        description = txtDescription,
        photo = imgThumbnail,
        isLiked = isLiked,
        videoCategoryId = videoCategoryId

    )
}