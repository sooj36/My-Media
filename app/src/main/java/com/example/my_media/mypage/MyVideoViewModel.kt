package com.example.my_media.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.text.FieldPosition

//화면 전환 시에도 ui가 데이터 안가져와도 됨

class MyVideoViewModel : ViewModel() {
    private val _likeList: MutableLiveData<List<MyVideoModel>> = MutableLiveData(mutableListOf())
    val likeList: LiveData<List<MyVideoModel>> get() = _likeList

    fun addLikeItem(item: MyVideoModel) {
        val items = _likeList.value?.toMutableList() ?: mutableListOf()
        val existingItemIndex = items.indexOfFirst { it.title == item.title }
        if (existingItemIndex != -1) {
            // 아이템이 이미 존재하면 해당 아이템을 업데이트하거나 대체
            items[existingItemIndex] = item
        } else {
            // 아이템이 존재하지 않으면 리스트에 추가
            items.add(0, item)
        }
        _likeList.value = ArrayList(items)
        Log.d("jun","추가된 아이템$items")
    }


    fun removeLikeItem(item: MyVideoModel) {
        val items = _likeList.value?.toMutableList() ?: mutableListOf()
        val itemToRemove = items.find { it.title == item.title }
        itemToRemove?.let {
            items.remove(it)
            _likeList.value = ArrayList(items) //ㅅ제거후 새로운 리스트를 만들어 할당
            Log.d("jun","제거된 아이템$items")
        }
    }
}

class MyVideoViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyVideoViewModel::class.java)) {
            return MyVideoViewModel() as T
        }
        throw IllegalAccessException("not found ViewModel class.") //호환되지 않은 경우 알림
    }
}