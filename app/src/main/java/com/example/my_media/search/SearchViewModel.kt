package com.example.my_media.search

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SearchViewModel (private val context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }
    companion object {
        private const val SHARED_PREF_NAME = "VideoSearchQueries"
        private const val KEY_SEARCH_QUERIES = "searchQueries"
    }

//    fun getSearchQueries(): List<String> {
//        val queriesJson = sharedPreferences.getString(KEY_SEARCH_QUERIES, null)
//        return if (!queriesJson.isNullOrEmpty()) {
//            Gson().fromJson(queriesJson, object : TypeToken<List<String>>() {}.type)
//        } else {
//            emptyList()
//        }
//    }


}