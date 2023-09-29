package com.example.my_media.util

import android.content.Context
import android.widget.Toast


   internal fun Context.showToast(message: String, time: Int ){
        Toast.makeText(this,message,time).show()
    }
