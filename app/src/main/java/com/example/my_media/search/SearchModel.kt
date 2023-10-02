package com.example.my_media.search

import android.os.Parcelable
import com.example.my_media.home.popular.HomePopularModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchModel (
    var searchedTitle: String,
    var searchdes: String,
    var searchedVideo: String,
    var isLiked : Boolean

): Parcelable
fun SearchModel.toHomePopularModel(): HomePopularModel {
    return HomePopularModel(
        txtTitle = searchedTitle,
        txtDescription = searchdes,
        imgThumbnail = searchedVideo,
        isLiked = isLiked
    )
}