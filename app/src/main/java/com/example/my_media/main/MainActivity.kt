package com.example.my_media.main

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.my_media.BuildConfig
import com.example.my_media.R
import com.example.my_media.databinding.ActivityMainBinding
import com.example.my_media.home.HomeFragment
import com.example.my_media.mypage.MyVideoFragment
import com.example.my_media.search.SearchFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val googleAuthLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                account.idToken?.let { initView(it) }
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
            .requestIdToken(BuildConfig.GOOGLE_CLIENT_ID)
//            .requestServerAuthCode(BuildConfig.GOOGLE_CLIENT_ID)
            .requestEmail()
            .requestScopes(Scope("https://www.googleapis.com/auth/youtube.force-ssl"))
//            .requestScopes(Scope(YouTubeScopes.YOUTUBE_READONLY))
            .build()
        val googleSignInClient = GoogleSignIn.getClient(this, googleSignInOption)

        googleSignInClient.signOut()
        googleAuthLauncher.launch(googleSignInClient.signInIntent)
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