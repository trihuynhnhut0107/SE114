package com.example.se114.domain.repository

import androidx.compose.runtime.MutableState
import com.example.se114.data.model.Member

interface IListMember {
    val members: MutableState<List<Member>>

    fun createNewPublicList(memberId: String, onSuccessListener: () -> Unit, onFailureListener: () -> Unit): String
    fun createNewPrivateList(memberId: String, onSuccessListener: () -> Unit, onFailureListener: () -> Unit): String

    fun addMember(listId: String, memberId: String, timeStamp: String?=null, onSuccessListener: () -> Unit, onFailureListener: () -> Unit)
    fun addMemberPrivate(listId: String, memberId: String, timeStamp: String?=null, onSuccessListener: () -> Unit, onFailureListener: () -> Unit)

    fun removeMemember(listId: String, memberId: String, onSuccessListener: () -> Unit, onFailureListener: () -> Unit)
    fun removeMememberPrivate(listId: String, memberId: String, onSuccessListener: () -> Unit, onFailureListener: () -> Unit)

    fun retrieveMemberList(listId: String, addOnCompleteListener: () -> Unit)
    fun retrieveMemberPrivateList(listId: String, addOnCompleteListener: () -> Unit)

    fun removeList(listId: String, onSuccessListener: () -> Unit, onFailureListener: () -> Unit)
    fun removePrivateList(listId: String, onSuccessListener: () -> Unit, onFailureListener: () -> Unit)

    fun depopulateMemberList(addOnCompleteListener: () -> Unit)
}