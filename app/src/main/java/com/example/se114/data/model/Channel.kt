package com.example.se114.data.model

data class Channel(
    var channelId: String?="",
    var channelName: String?="",
    var creatorId: String?="",
    var createdAt: String?="",
    var listMemberId: String?="",
    var public: Boolean=true
) {
    fun toMap() = mapOf(
        "channelId" to channelId,
        "channelName" to channelName,
        "creatorId" to creatorId,
        "createdAt" to createdAt,
        "listMemberId" to listMemberId,
        "public" to public,
    )
}