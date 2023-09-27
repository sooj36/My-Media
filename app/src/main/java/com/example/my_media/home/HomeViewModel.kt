package com.example.my_media.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.my_media.data.RetrofitClient
import com.example.my_media.data.YoutubeRemoteDataSource
import com.example.my_media.data.YoutubeRepositoryImpl
import com.example.my_media.home.subscribe.HomeSubscribeModel
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HomeViewModel(private val youtubeRepositoryImpl: YoutubeRepositoryImpl): ViewModel() {
    private val _subscribeList: MutableLiveData<List<HomeSubscribeModel>> = MutableLiveData()
    val subscribeList: LiveData<List<HomeSubscribeModel>> get() = _subscribeList

    fun getSubscribeList(accessToken: String) {
        viewModelScope.launch {
            try {
                val responseSubscribeData = youtubeRepositoryImpl.getSubscribe(accessToken).items
                val subscribeItems = ArrayList<HomeSubscribeModel>()
                responseSubscribeData.forEach {
                    subscribeItems.add(
                        HomeSubscribeModel(
                            it.snippet.thumbnails.medium.url,
                            it.snippet.title
                        )
                    )
                }
                val currentList = subscribeList.value.orEmpty().toMutableList()
                currentList.addAll(subscribeItems)
                _subscribeList.postValue(currentList)
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }
}

class HomeViewModelFactory : ViewModelProvider.Factory {
    private val repository = YoutubeRepositoryImpl(RetrofitClient.service)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Not Found ViewModel Class")
        }
    }
}