package com.example.my_media.mypage

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.my_media.R
import com.example.my_media.util.ContextProvider
import com.example.my_media.util.ContextProviderImpl
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.FieldPosition

//화면 전환 시에도 ui가 데이터 안가져와도 됨

class MyVideoViewModel(private val contextProvider: ContextProvider) : ViewModel() {
    private val _likeList: MutableLiveData<List<MyVideoModel>> = MutableLiveData(mutableListOf())
    val likeList: LiveData<List<MyVideoModel>> get() = _likeList

    private val pref = contextProvider.getSharedPreferences()
    private val prefKey = contextProvider.getString(R.string.pref_key_favorite_list)

    fun setSharedPrefsList() {
        val gson = Gson()
        val jsonList = gson.toJson(_likeList.value)
        pref.edit().apply {
            putString(prefKey, jsonList)
            apply()
        }
    }

    fun getSharedPrefsList() {
        val jsonList = pref.getString(prefKey, "") ?: return

        val gson = Gson()
        val type = object: TypeToken<List<MyVideoModel>>() {}.type
        val prefsList = gson.fromJson<List<MyVideoModel>>(jsonList, type) ?: return
        val currentList = likeList.value.orEmpty().toMutableList()
        currentList.addAll(prefsList)
        _likeList.value = currentList
    }

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
            _likeList.value = ArrayList(items)
            Log.d("jun","제거된 아이템$items")
        }
    }
}
class MyVideoViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyVideoViewModel::class.java)) {
            return MyVideoViewModel(ContextProviderImpl(context)) as T
        }
        throw IllegalAccessException("not found ViewModel class.")
    }
}