package com.example.my_media.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.my_media.data.RetrofitClient
import com.example.my_media.data.YoutubeRepositoryImpl
import com.example.my_media.home.subscribe.HomeSubscribeModel
import kotlinx.coroutines.launch
import com.example.my_media.home.popular.HomePopularModel

class HomeViewModel(private val youtubeRepositoryImpl: YoutubeRepositoryImpl) : ViewModel() {
    private val _subscribeList: MutableLiveData<List<HomeSubscribeModel>> = MutableLiveData()
    val subscribeList: LiveData<List<HomeSubscribeModel>> get() = _subscribeList

    private val _popularVideoList: MutableLiveData<List<HomePopularModel>> = MutableLiveData()
    val popularVideoList: LiveData<List<HomePopularModel>> get() = _popularVideoList

    fun getSubscribeList(accessToken: String) {
        viewModelScope.launch {
            runCatching {
                val response = youtubeRepositoryImpl.getSubscribe(accessToken).items
                val subscribeItems = ArrayList<HomeSubscribeModel>()

                response?.forEach {
                    subscribeItems.add(
                        HomeSubscribeModel(
                            it.subscribeSnippet?.subscribeThumbnails?.default?.url.orEmpty(),
                            it.subscribeSnippet?.title.orEmpty()
                        )
                    )
                }
                val currentList = subscribeList.value.orEmpty().toMutableList()
                currentList.addAll(subscribeItems)
                _subscribeList.postValue(currentList)
            }.onFailure {
                Log.e("Network Failed", it.message.toString())
            }
        }
    }

    fun getPopularVideo(accessToken: String, videoCategoryId : String) { //카테고리 아이디를 파라미터로 받기
        viewModelScope.launch {
            val response = youtubeRepositoryImpl.getPopularVideo(accessToken, videoCategoryId).items // 모든 데이터
            val popularVideoItems = ArrayList<HomePopularModel>()
            response.forEach {
                popularVideoItems.add(
                    HomePopularModel(
                        txtTitle = it.popularSnippet?.title.orEmpty(),
                        txtDescription = it.popularSnippet?.description.orEmpty(),
                        imgThumbnail = it.popularSnippet?.popularThumbnails?.standard?.url.orEmpty(),
                        isLiked = false

                    )
                )
            }// 일부 값 추출
            _popularVideoList.value = popularVideoItems // 호출
        }
    }
}

class HomeViewModelFactory : ViewModelProvider.Factory {
    private val repository = YoutubeRepositoryImpl(RetrofitClient.service)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Not Found ViewModel Class")
        }
    }
}