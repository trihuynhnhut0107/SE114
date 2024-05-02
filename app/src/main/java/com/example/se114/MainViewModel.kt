package com.example.se114

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.se114.data.model.UserData
import com.example.se114.domain.repository.IAuthenticator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainViewModel @Inject constructor(
    @Named("firebase-auth") private val auth: IAuthenticator
): ViewModel() {

    var isLog = mutableStateOf<Boolean>(false)

    fun getUser(): UserData? {
         return auth.getCurrentUser()
    }

    fun signIn() {
        auth.signIn(
            email = "123sad@gmail.com",
            password = "1234567",
            onSuccessListener = { isLog.value = (getUser() != null)},
            onFailureListener = {} )
    }
}