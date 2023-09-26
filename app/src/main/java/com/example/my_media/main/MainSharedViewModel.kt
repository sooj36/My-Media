package com.example.my_media.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.my_media.mypage.MyVideoModel

class MainSharedViewModel : ViewModel() {
    private val _likeEvent : MutableLiveData<MainSharedEventforLike> = MutableLiveData()
    val likeEvent: LiveData<MainSharedEventforLike> get() = _likeEvent

    fun toggleLikeItem(item :MyVideoModel){ _likeEvent.value =  // 아이템의 현재 좋아요 상태에 따라서 이벤트 생성 하고_likeEvent에 할당
            if (item.isLiked){
                MainSharedEventforLike.AddLikeItem(item) //true인경우 애드이벤트생성
        }else
                MainSharedEventforLike.RemoveLikeItem(item)
    }
}
 //좋아요 이벤트를 관리하는  MainSharedEventforLike 인ㄴ터페이스정의!
sealed interface  MainSharedEventforLike{
    data class AddLikeItem(val item: MyVideoModel) : MainSharedEventforLike //아이템정보포함 아이템추가하는이벤트

    data class RemoveLikeItem ( val item :MyVideoModel) :MainSharedEventforLike
}