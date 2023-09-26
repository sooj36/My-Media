package com.example.my_media.home


// json응답 데이터 모델링 , recyclerview와 연결

data class HomeModel(
    val id: Long,
    val imgThumbnail: String,
    val txtTitle: String
)