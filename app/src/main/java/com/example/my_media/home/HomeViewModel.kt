package com.example.my_media.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.my_media.data.RepositoryImpl

class HomeViewModel(private val repositoryImpl: RepositoryImpl): ViewModel() {
    private val _list: MutableLiveData<List<HomeModel>> = MutableLiveData()
    val list: LiveData<List<HomeModel>> get() = _list


}

class HomeViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(RepositoryImpl()) as T
        } else {
            throw IllegalArgumentException("Not Found ViewModel Class")
        }
    }
}