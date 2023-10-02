package com.example.my_media.main

import android.accounts.Account
import android.app.Activity
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
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

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(applicationContext as Application)
    }



    private val googleAuthLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                viewModel.useAccessToken(account?.account)
            } catch (e: ApiException) {
                Log.e("GoogleSignIn", "Google Sign-In failed: ${e.statusCode}, message: ${e.message}")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        requestGoogleLogin()
    }

    private fun initViewModel() = with(viewModel) {
        tokenData.observe(this@MainActivity) { token ->
            initView(token)
        }
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