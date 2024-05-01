package com.example.se114.domain.repository

import androidx.compose.runtime.MutableState
import com.example.se114.data.model.Member

interface IChannel {
    val members: MutableState<List<Member>>

    fun createPublicChannel(creatorId: String, name: String, listId: String?=null, listMemberId: String?=null, onSuccessListener: () -> Unit, onFailureListener: () -> Unit) : String
    fun createPrivateChannel(creatorId: String, name: String, listId: String?=null, onSuccessListener: () -> Unit, onFailureListener: () -> Unit) : String

    fun retrievePublicMember(channelId: String, addOnCompleteListener: () -> Unit)
    fun retrieveMemberPrivate(channelId: String, addOnCompleteListener: () -> Unit)

    fun addMemberPrivateList(channelId: String, list: List<Member>, onSuccessListener: () -> Unit, onFailureListener: () -> Unit)

    fun removeChannel(channelId: String, onSuccessListener: () -> Unit, onFailureListener: () -> Unit)
}