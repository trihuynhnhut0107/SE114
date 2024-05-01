package com.example.se114.domain.repository

import com.example.se114.data.model.UserData

interface IAuthenticator {
    fun signIn(email: String, password: String, onSuccessListener: () -> Unit, onFailureListener: () -> Unit)
    fun signOut(onCompleteListener: () -> Unit)
    fun signUp(email: String, password: String, onSuccessListener: () -> Unit, onFailureListener: () -> Unit)
    fun getCurrentUser() : UserData?
}