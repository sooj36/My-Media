package com.example.my_media.home

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

class HomeViewModel(private val repositoryImpl: RepositoryImpl) : ViewModel() {
    private val _showPopularVideo: MutableLiveData<List<HomeModel>> = MutableLiveData()
    val list: LiveData<List<HomeModel>> get() = _showPopularVideo

    fun getPopularVideo(itemList: ArrayList<HomeModel>) {
        val currentList = list.value.orEmpty().toMutableList()
        currentList.addAll(itemList)
        _showPopularVideo.value = currentList
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