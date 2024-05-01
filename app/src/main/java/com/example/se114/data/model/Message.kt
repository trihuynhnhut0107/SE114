package com.example.se114.data.model

data class Message(
    val msg: String,
    val userId: String,
    val userName: String,
    val timestamp: Long
) {
    fun toMap() = mapOf(
        "msg" to msg,
        "userId" to userId,
        "userName" to userName,
        "timestamp" to timestamp,
    )
}
