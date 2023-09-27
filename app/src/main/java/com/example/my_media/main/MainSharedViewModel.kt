package com.example.my_media.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.my_media.home.popular.HomePopularModel
import com.example.my_media.home.popular.toMyVideoModel
import com.example.my_media.mypage.MyVideoModel

class MainSharedViewModel : ViewModel() {
    private val _likeEvent: MutableLiveData<List<MainSharedEventforLike>> = MutableLiveData(listOf())//단일객체에서 여러객체로 선언 이벤트 발생할때마다 이벤트 누적시키려고
    val likeEvent: LiveData<List<MainSharedEventforLike>> get() = _likeEvent
    //하트상태관리 라이브데이터
    private val _likedStatus = MutableLiveData<HashMap<String, Boolean>>()
//    val likedStatus: LiveData<HashMap<String, Boolean>> = _likedStatus

    fun updateLikeStatus(videoId: String, isLiked: Boolean) {
        val currentStatus = _likedStatus.value ?: hashMapOf()
        currentStatus[videoId] = isLiked
        _likedStatus.value = currentStatus
    }

    fun getLikeStatus(videoTitle: String): Boolean {
        return _likedStatus.value?.get(videoTitle) ?: false
    }

    fun toggleLikeItem(item: HomePopularModel?) {
        item ?: return
        val videoId = item.txtTitle
        val isLiked = getLikeStatus(videoId)

        val event = if (isLiked) {
            MainSharedEventforLike.AddLikeItem(item.toMyVideoModel())
        } else {
            MainSharedEventforLike.RemoveLikeItem(item.toMyVideoModel())
        }

        val currentEvents = _likeEvent.value?.toMutableList() ?: mutableListOf()
        currentEvents.add(event)
        _likeEvent.value = currentEvents
    }
}

//좋아요 이벤트를 관리하는  MainSharedEventforLike 인터페이스정의!
sealed interface MainSharedEventforLike {
    data class AddLikeItem(val item: MyVideoModel) : MainSharedEventforLike //아이템정보포함 아이템추가하는이벤트

    data class RemoveLikeItem(val item: MyVideoModel) : MainSharedEventforLike
}