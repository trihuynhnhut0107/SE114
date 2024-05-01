package com.example.se114.data.model

import androidx.compose.runtime.MutableState

data class UserData(
    var userId: String? = "",
    var name: String? = "",
    var email: String? = "",
    var imageUrl: String? = ""
) {
    fun toMap() = mapOf(
        "userId" to userId,
        "name" to name,
        "email" to email,
        "imageUrl" to imageUrl,
    )
}
