package com.example.my_media.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

//화면 전환 시에도 ui가 데이터 안가져와도 됨

class MyVideoViewModel: ViewModel() {
    companion object {
        private val currentList: MutableList<MyVideoModel> = mutableListOf()//마이모델의 전체인스턴스공유되는 리스트 한개로통일
    }

    private val _likeList: MutableLiveData<MutableList<MyVideoModel>> = MutableLiveData(currentList)
    val likeList: LiveData<MutableList<MyVideoModel>> get() = _likeList

    fun addLikeItem(item: MyVideoModel) {
        if (!currentList.contains(item)) {
            currentList.add(0, item)
            _likeList.value = currentList
            Log.d("jun", "추가 $currentList")
        }
    }

    fun removeLikeItem(item: MyVideoModel){
        if (currentList.contains(item)) {
            currentList.remove(item)
            _likeList.value = currentList
            Log.d("jun", "제거 $currentList")
        }
    }
}


class MyVideoViewModelFactory :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyVideoViewModel::class.java)){
            return MyVideoViewModel() as T
        }
       throw IllegalAccessException("not found ViewModel class.") //호환되지 않은 경우 알림
    }
}