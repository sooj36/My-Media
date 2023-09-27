package com.example.my_media.home

import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.my_media.data.PopularVideosResponse
import com.example.my_media.data.Repository
import com.example.my_media.data.RepositoryImpl
import com.example.my_media.data.RetrofitInterface
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {
    private val _showPopularVideo: MutableLiveData<List<HomeModel>> = MutableLiveData()
    val list: LiveData<List<HomeModel>> get() = _showPopularVideo

    fun getPopularVideo(itemList: ArrayList<HomeModel>) {
        viewModelScope.launch {
            val response = repository.getPopularVideo().items // 모든 데이터
            val popularVideoItems = ArrayList<HomeModel>()
            response.forEach {
                popularVideoItems.add(
                    HomeModel(
                        it.snippet.title,
                        it.snippet.description,
                        it.snippet.thumbnails
                    )
                )
            }// 일부 값 추출
        }
    }
}

class HomeViewModelFactory(private val service : RetrofitInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(RepositoryImpl(service)) as T
        } else {
            throw IllegalArgumentException("Not Found ViewModel Class")
        }
    }
}