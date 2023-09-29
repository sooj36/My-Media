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
        val findItem = items.find { it.title == item.title }
        if (findItem == null){
            items.add(0,item)
        }else{
            val i = items.indexOf(findItem)
            items[i] = item
        }
        _likeList.value = ArrayList(items)
    }

    fun removeLikeItem(item: MyVideoModel) {
        val items = _likeList.value?.toMutableList() ?: mutableListOf()
        val findRemoveItem = items.find { it.title == item.title }
        findRemoveItem?.let {
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