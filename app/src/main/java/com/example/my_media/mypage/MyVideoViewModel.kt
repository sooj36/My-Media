package com.example.my_media.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


//화면 전환 시에도 ui가 데이터 안가져와도 됨

class MyVideoViewModel:ViewModel() {
    private val _likeList : MutableLiveData<List<MyVideoModel>> = MutableLiveData(mutableListOf())
    val likeList : LiveData<List<MyVideoModel>> get() = _likeList

    fun addLikeItem(item: MyVideoModel) {
        val items = _likeList.value.orEmpty().toMutableList()
        if (!items.contains(item)) {
            items.add(0, item)
            _likeList.value = items
        }
    }

    fun removeLikeItem(item: MyVideoModel){
        val items = _likeList.value.orEmpty().toMutableList()
        if (items.contains(item))
            items.remove(item)
        _likeList.postValue(items)
    }

}








class MyVideoViewModelFactory :ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyVideoViewModel::class.java)){
            return MyVideoViewModel() as T //파라미터 라이크한 데이터들 넣기전 임의로 넣은거
        }
       throw IllegalAccessException("not found ViewModel class.") //호환되지 않은 경우 알림
    }


}