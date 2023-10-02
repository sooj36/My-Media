package com.example.my_media.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.my_media.home.popular.HomePopularModel
import com.example.my_media.home.popular.toMyVideoModel
import com.example.my_media.mypage.MyVideoModel

class MainSharedViewModel : ViewModel() {
    private val _likeEvent : MutableLiveData<MutableList<MainSharedEventforLike>> = MutableLiveData() //단일객체에서 변경
    val likeEvent: LiveData<MutableList<MainSharedEventforLike>> get() = _likeEvent
    private val likeStatusMap: MutableMap<String, Boolean> = mutableMapOf()

    fun toggleLikeItem(item: HomePopularModel) {
        val currentStatus = likeStatusMap[item.txtTitle] ?: false
        val newStatus = !currentStatus
        likeStatusMap[item.txtTitle] = newStatus

        val event = if (newStatus) {
            MainSharedEventforLike.AddLikeItem(item.toMyVideoModel())
        } else {
            MainSharedEventforLike.RemoveLikeItem(item.toMyVideoModel())
        }

        val currentEvents = _likeEvent.value ?: mutableListOf()
        currentEvents.add(event)

        _likeEvent.value = currentEvents
    }

    fun getLikeStatus(videoId: String): Boolean {
        return likeStatusMap[videoId] ?: false
    }
}


sealed interface MainSharedEventforLike {
    data class AddLikeItem(val item: MyVideoModel) : MainSharedEventforLike
    data class RemoveLikeItem(val item: MyVideoModel) : MainSharedEventforLike
}
