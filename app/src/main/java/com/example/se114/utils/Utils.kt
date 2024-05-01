package com.example.se114.utils

import android.util.Log


fun handleException(exception: Exception?=null, customMessage: String?="") {
    exception?.printStackTrace()
    val errorMsg = exception?.localizedMessage?: ""
    val message = if(customMessage.isNullOrEmpty()) errorMsg else customMessage

    Log.e("CUSTOM ERROR",  message)
}
