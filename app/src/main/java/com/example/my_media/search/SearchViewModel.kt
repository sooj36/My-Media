package com.example.my_media.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.my_media.data.RetrofitClient
import com.example.my_media.data.YoutubeRepositoryImpl
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SearchViewModel(private val youtubeRepositoryImpl: YoutubeRepositoryImpl) : ViewModel() {
    private val _list: MutableLiveData<List<SearchModel>> = MutableLiveData()
    val list: LiveData<List<SearchModel>> get() = _list

    fun getSearchVideo(query: String) {
        viewModelScope.launch {
            try {
                val response = youtubeRepositoryImpl.getSearchVideo(query =  query).items
                val searchItems = ArrayList<SearchModel>()
                response.forEach {
                    searchItems.add(
                        SearchModel(
                            it.searchSnippet.title,
                            it.searchSnippet.searchThumbnails.high.url
                        )
                    )
                }
                val currentList = list.value.orEmpty().toMutableList()
                currentList.addAll(searchItems)
                _list.postValue(currentList)
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }
}

class SearchViewModelFactory : ViewModelProvider.Factory {
    private val repository = YoutubeRepositoryImpl(RetrofitClient.service)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Not Found ViewModel Class")
        }
    }
}