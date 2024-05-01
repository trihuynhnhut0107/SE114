package com.example.se114.data.model

data class Member(
    var userId: String?="",
    var joinedAt: String?= null,
) {
    fun toMap() = mapOf(
        "userId" to userId,
        "joinedAt" to joinedAt
    )
}

data class MemberListId(
    var listId: String?="",
) {
    fun toMap() = mapOf(
        "listId" to listId
    )
}
