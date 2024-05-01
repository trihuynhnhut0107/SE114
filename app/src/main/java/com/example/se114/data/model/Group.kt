package com.example.se114.data.model

data class Group(
    var groupId: String?="",
    var groupName: String?="",
    var creatorId: String?="",
    var listMemberId: String?="",
    var totalMember: Int?=null,
    var createdAt: String?=null,
) {
    fun toMap() = mapOf(
        "groupId" to groupId,
        "groupName" to groupName,
        "creatorId" to creatorId,
        "listMemberId" to listMemberId,
        "totalMember" to totalMember,
        "createdAt" to createdAt,
    )
}

data class GroupId(
    var groupId: String?="",
) {
    fun toMap() = mapOf(
        "groupId" to groupId,
    )
}


