package com.example.my_media.main

import android.accounts.Account
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.my_media.R
import com.example.my_media.databinding.ActivityMainBinding
import com.example.my_media.home.HomeFragment
import com.example.my_media.mypage.MyVideoFragment
import com.example.my_media.search.SearchFragment
import com.google.android.gms.auth.GoogleAuthException
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException
import com.google.android.gms.auth.UserRecoverableAuthException
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.api.services.youtube.YouTubeScopes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val googleAuthLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        Log.d("jun","${result.resultCode}")
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                useAccessToken(account?.account)
            } catch (e: ApiException) {
                Log.e("GoogleSignIn", "Google Sign-In failed: ${e.statusCode}, message: ${e.message}")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestGoogleLogin()
    }

    private fun requestGoogleLogin() {
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestScopes(Scope(YouTubeScopes.YOUTUBE_READONLY))
            .build()
        val googleSignInClient = GoogleSignIn.getClient(this, googleSignInOption)

        googleSignInClient.signOut()
        googleAuthLauncher.launch(googleSignInClient.signInIntent)
    }

    private fun useAccessToken(account: Account?) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val accountDetails = Account(
                    account?.name ?: "",
                    GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE
                )
                val token = GoogleAuthUtil.getToken(
                    applicationContext,
                    accountDetails,
                    "oauth2:${YouTubeScopes.YOUTUBE_READONLY}"
                )
                initView(token)
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

    private fun initView(accessToken: String) = with(binding) {
        val homeFragment = HomeFragment.newInstance(accessToken)
        supportFragmentManager.beginTransaction().add(R.id.frameLayout, homeFragment).commit()

        bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.menu_home -> supportFragmentManager.beginTransaction().replace(R.id.frameLayout, homeFragment).commit()
                R.id.menu_search -> supportFragmentManager.beginTransaction().replace(R.id.frameLayout, SearchFragment.newInstance()).commit()
                R.id.menu_my_video -> supportFragmentManager.beginTransaction().replace(R.id.frameLayout, MyVideoFragment.newInstance()).commit()
            }
            true
        }
    }
}