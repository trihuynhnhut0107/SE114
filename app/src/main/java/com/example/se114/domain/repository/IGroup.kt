package com.example.se114.domain.repository

import android.os.Parcelable.Creator
import androidx.compose.runtime.MutableState
import com.example.se114.data.model.Group
import com.example.se114.data.model.GroupId
import com.example.se114.data.model.Member

interface IGroup {
    val groupList: MutableState<List<GroupId>>
    val membersInGroup: MutableState<List<Member>>
    val privateChannelList: MutableState<List<String>>
    val publicChannelList: MutableState<List<String>>

    suspend fun getGroup(groupId : String) : Group?

    fun retrieveGroupList(userId: String, addOnCompleteListener: () -> Unit)
    fun retrieveMemberList(groupId: String, addOnCompleteListener: () -> Unit)

    fun createNewGroup(creatorId: String, groupName: String, onSuccessListener: () -> Unit, onFailureListener: () -> Unit)
    fun removeGroup(groupId: String, onSuccessListener: () -> Unit, onFailureListener: () -> Unit)

    fun createNewPrivateChannel(creatorId: String ,groupId: String, name: String, memberIdList: List<String>, onSuccessListener: () -> Unit, onFailureListener: () -> Unit)
    fun createNewPublicChannel(creatorId: String ,groupId: String, name: String, onSuccessListener: () -> Unit, onFailureListener: () -> Unit)

    fun addMember(groupId: String, list: List<Member>, onSuccessListener: () -> Unit, onFailureListener: () -> Unit)
    fun addMember(groupId: String, memberId: String, onSuccessListener: () -> Unit, onFailureListener: () -> Unit)
    fun removeMember(groupId: String, memberId: String, onSuccessListener: () -> Unit, onFailureListener: () -> Unit)

    fun depopulateMemberList( addOnCompleteListener: () -> Unit)
}