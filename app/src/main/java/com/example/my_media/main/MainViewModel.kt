package com.example.my_media.main

import android.accounts.Account
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.GoogleAuthException
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException
import com.google.android.gms.auth.UserRecoverableAuthException
import com.google.api.services.youtube.YouTubeScopes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _tokenData: MutableLiveData<String> = MutableLiveData()
    val tokenData: LiveData<String> get() = _tokenData

    fun useAccessToken(account: Account?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val accountDetails = Account(
                    account?.name ?: "",
                    GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE
                )
                val token = GoogleAuthUtil.getToken(
                    getApplication<Application>().applicationContext,
                    accountDetails,
                    "oauth2:${YouTubeScopes.YOUTUBE_READONLY}"
                )
                _tokenData.postValue(token)
            } catch (e: GooglePlayServicesAvailabilityException) { // Google Play 서비스를 업데이트해야 할 때 처리
                Log.e("GooglePlayServicesAvailabilityException", "message: ${e.message}")
            } catch (e: UserRecoverableAuthException) { // 사용자에게 권한을 요청할 때 처리
                Log.e("UserRecoverableAuthException", "message: ${e.message}")
            } catch (e: GoogleAuthException) { // 인증 예외 처리
                Log.e("GoogleAuthException", "message: ${e.message}")
            } catch (e: IOException) { // 네트워크 또는 I/O 예외 처리
                Log.e("IOException", "message: ${e.message}")
            }
        }
    }
}

class MainViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application) as T
        } else {
            throw IllegalArgumentException("Not Found ViewModel Class")
        }
    }
}