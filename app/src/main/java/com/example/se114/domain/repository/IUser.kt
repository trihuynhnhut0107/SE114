package com.example.se114.domain.repository

import androidx.compose.runtime.MutableState
import com.example.se114.data.model.UserData

interface IUser {
    var groupList: MutableState<List<String>>

    fun createUser(userData: UserData, onSuccessListener: () -> Unit, onFailureListener: () -> Unit)
    fun updateUser(userData: UserData, onSuccessListener: () -> Unit, onFailureListener: () -> Unit)
    fun getUserData(userId: String) : UserData
}