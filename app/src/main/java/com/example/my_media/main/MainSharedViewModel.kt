package com.example.my_media.main

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.my_media.R
import com.example.my_media.home.popular.HomePopularModel
import com.example.my_media.home.popular.toMyVideoModel
import com.example.my_media.mypage.MyVideoModel
import com.example.my_media.util.ContextProvider
import com.example.my_media.util.ContextProviderImpl
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken



class MainSharedViewModel(private val contextProvider: ContextProvider) : ViewModel() {
    private val _likeEvent: MutableLiveData<MutableList<MainSharedEventforLike>> =
        MutableLiveData() //단일객체에서 변경
    val likeEvent: LiveData<MutableList<MainSharedEventforLike>> get() = _likeEvent
    private val likeStatusMap: MutableMap<String, Boolean> = mutableMapOf() //좋아요 상태저장맵

    private val pref = contextProvider.getSharedPreferences()
    private val prefKey = contextProvider.getString(R.string.pref_key_favorite_status)

     fun saveLikeStatus() {
        val edit = pref.edit()
        val gson = Gson()
        val json = gson.toJson(likeStatusMap)
         edit.putString(prefKey, json)
         edit.apply()
    }
     fun loadLikeStatus() {
        val gson = Gson()
        val json = pref.getString(prefKey, "")
        val type = object : TypeToken<Map<String, Boolean>>() {}.type
        val loadedMap: Map<String, Boolean> = gson.fromJson(json, type) ?: mapOf()
        likeStatusMap.putAll(loadedMap)
    }
init {
    loadLikeStatus()
}
    fun toggleLikeItem(item: HomePopularModel) {
        val newStatus = item.isLiked
        likeStatusMap[item.txtTitle] = newStatus

        val event = if (newStatus) {
            MainSharedEventforLike.AddLikeItem(item.toMyVideoModel())
        } else {
            MainSharedEventforLike.RemoveLikeItem(item.toMyVideoModel())
        }
        val currentEvents = _likeEvent.value ?: mutableListOf()
        currentEvents.add(event)
        _likeEvent.value = currentEvents
    }
    fun getLikeStatus(videoId: String): Boolean {
        return likeStatusMap[videoId] ?: false
    }
}

sealed interface MainSharedEventforLike {
    data class AddLikeItem(val item: MyVideoModel) : MainSharedEventforLike
    data class RemoveLikeItem(val item: MyVideoModel) : MainSharedEventforLike
}

class MainSharedViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainSharedViewModel::class.java)) {
            return MainSharedViewModel(ContextProviderImpl(context)) as T
        }
        throw IllegalArgumentException("bot found ViewModel class")
    }
}
