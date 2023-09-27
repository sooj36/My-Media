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
    val list: LiveData<List<HomeModel>> get() = _showPopularVideo // 값 변경 관찰


    fun getPopularVideo() { //카테고리 아이디를 파라미터로 받기
        viewModelScope.launch {
            val response = repository.getPopularVideo().items // 모든 데이터
            Log.d("sooj", "$response")
            val popularVideoItems = ArrayList<HomeModel>()
            response.forEach {
                Log.d("sooj", "${it.snippet.thumbnails.standard.url}")
                popularVideoItems.add(
                    HomeModel(
                        txtTitle = it.snippet.title,
                        txtDescription = it.snippet.description,
                        imgThumbnail = it.snippet.thumbnails.standard.url
                    )
                )
            }// 일부 값 추출
            _showPopularVideo.value = popularVideoItems // 호출
        }
    }
}

class HomeViewModelFactory(private val service: RetrofitInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(RepositoryImpl(service)) as T
        } else {
            throw IllegalArgumentException("Not Found ViewModel Class")
        }
    }
}