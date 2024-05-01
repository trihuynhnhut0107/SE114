package com.example.se114.data.repository

import androidx.compose.runtime.mutableStateOf
import com.example.se114.data.model.Group
import com.example.se114.data.model.GroupId
import com.example.se114.data.model.Member
import com.example.se114.domain.repository.IChannel
import com.example.se114.domain.repository.IGroup
import com.example.se114.domain.repository.IListMember
import com.example.se114.exception.CustomException
import com.example.se114.utils.Constant
import com.example.se114.utils.handleException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CompletableDeferred
import java.util.Calendar
import javax.inject.Inject

class FirebaseGroupManager @Inject constructor(
    private val _database: FirebaseFirestore,
    private  val _channelManager: IChannel,
    private val _memberListManager: IListMember,
) : IGroup {
    private val _userReference = _database.collection(Constant.USER_NODE)
    private val _groupReference = _database.collection(Constant.GROUP_NODE)

    override val groupList = mutableStateOf<List<GroupId>>(arrayListOf())
    override val membersInGroup = _channelManager.members

    override val privateChannelList = mutableStateOf<List<String>>(arrayListOf())
    override val publicChannelList = mutableStateOf<List<String>>(arrayListOf())

    override suspend fun getGroup(groupId: String): Group? {
        val gr = CompletableDeferred<Group?>()
        if (groupId == "") {
            handleException(CustomException.GroupNotExistedException)
            return null
        }
        _groupReference.document(groupId).get().addOnSuccessListener {
            if (it.exists()) {
                val tmp = it.toObject<Group>()
                gr.complete(tmp)
            } else {
                gr.complete(null)
            }
        }
        return gr.await()
    }

    override fun retrieveGroupList(userId: String, addOnCompleteListener: () -> Unit) {
        try {
            _userReference.document(userId)
                .collection(Constant.GROUP_LIST_NODE)
                .addSnapshotListener { value, error ->
                    if (error != null)
                        handleException(error)
                    value?.let {
                        groupList.value = value.documents.mapNotNull {
                            it.toObject<GroupId>()
                        }
                    }
                    addOnCompleteListener()
                }
        } catch (ex: Exception) {
            handleException(ex)
        }
    }

    override fun retrieveMemberList(groupId: String, addOnCompleteListener: () -> Unit) {
        _memberListManager.retrieveMemberList(groupId, addOnCompleteListener)
    }

    override fun createNewGroup(
        creatorId: String,
        groupName: String,
        onSuccessListener: () -> Unit,
        onFailureListener: () -> Unit
    ) {
       try {
           val now = Calendar.getInstance().time.toString()
           val groupId = _memberListManager.createNewPublicList(
               memberId = creatorId,
               onSuccessListener = onSuccessListener,
               onFailureListener = onFailureListener
           )

           val group = Group(
               groupId = groupId,
               groupName = groupName,
               creatorId = creatorId,
               listMemberId = groupId,
               totalMember = 1,
               createdAt = now
           )

           _groupReference.document(groupId).set(group)
           _userReference.document(creatorId)
               .collection(Constant.GROUP_LIST_NODE).document(groupId)
               .set(mapOf("groupId" to groupId))

           val channelId = _channelManager.createPublicChannel(
               creatorId = creatorId,
               name = "Chung",
               listId = groupId,
               listMemberId = groupId,
               onSuccessListener = onSuccessListener,
               onFailureListener = onFailureListener
           )

           _groupReference.document(groupId)
               .collection(Constant.CHANNEL_LIST_NODE).document(channelId)
               .set(mapOf("channelId" to channelId))
               .addOnSuccessListener { onSuccessListener() }
               .addOnFailureListener { onFailureListener() }
       } catch (ex: Exception) {
           handleException(ex)
       }
    }

    override fun removeGroup(
        groupId: String,
        onSuccessListener: () -> Unit,
        onFailureListener: () -> Unit
    ) {
        try {
            retrieveMemberList(groupId = groupId, {})
            membersInGroup.value.forEach {
                member ->
                member.userId?.let {
                    _userReference.document(it).collection(Constant.GROUP_LIST_NODE).document(groupId).delete()
                }
            }
            _groupReference.document(groupId)
                .collection(Constant.PRIVATE_CHANNEL_LIST_NODE).get()
                .addOnSuccessListener {
                    for (doc in it.documents) {
                        _memberListManager.removePrivateList(
                            doc.id,
                            onSuccessListener = {},
                            onFailureListener = onFailureListener
                        )
                        _channelManager.removeChannel(
                            doc.id,
                            onSuccessListener = {},
                            onFailureListener = onFailureListener
                        )
                    }
                    onSuccessListener()
                }
                .addOnFailureListener { onFailureListener() }

            _groupReference.document(groupId)
                .collection(Constant.CHANNEL_LIST_NODE).get()
                .addOnSuccessListener {
                    for (doc in it.documents) {
                        _channelManager.removeChannel(
                            doc.id,
                            onSuccessListener = {},
                            onFailureListener = {}
                        )
                    }
                    onSuccessListener()
                }
                .addOnFailureListener { onFailureListener() }

            _memberListManager.removeList(
                listId = groupId,
                onSuccessListener = {},
                onFailureListener = onFailureListener
            )

            _groupReference.document(groupId).delete()
                .addOnSuccessListener { onSuccessListener() }
                .addOnFailureListener { onFailureListener() }
        } catch (ex: Exception) {
            handleException(ex)
        }
    }

    override fun createNewPrivateChannel(
        creatorId: String,
        groupId: String,
        name: String,
        memberIdList: List<String>,
        onSuccessListener: () -> Unit,
        onFailureListener: () -> Unit
    ) {
        try {

            val channelId = _channelManager.createPrivateChannel(
                creatorId = creatorId,
                name = name,
                onSuccessListener = {},
                onFailureListener = onFailureListener
            )

            val now = Calendar.getInstance().time.toString()
            memberIdList.forEach {
                member ->
                _memberListManager.addMemberPrivate(
                    listId = channelId,
                    memberId = member,
                    timeStamp = now,
                    onSuccessListener = {},
                    onFailureListener = onFailureListener
                )
            }

            _groupReference.document(groupId)
                .collection(Constant.PRIVATE_CHANNEL_LIST_NODE).document(channelId)
                .set(mapOf("channelId" to channelId))
                .addOnSuccessListener { onSuccessListener() }
                .addOnFailureListener { onFailureListener() }

        } catch (ex: Exception) {
            handleException(ex)
        }
    }

    override fun createNewPublicChannel(
        creatorId: String,
        groupId: String,
        name: String,
        onSuccessListener: () -> Unit,
        onFailureListener: () -> Unit
    ) {
        try {
            val channelId = _channelManager.createPublicChannel(
                creatorId = creatorId,
                name = name,
                listId = groupId,
                onSuccessListener = {},
                onFailureListener = onFailureListener
            )

            _groupReference.document(groupId)
                .collection(Constant.CHANNEL_LIST_NODE).document(channelId)
                .set(mapOf("channelId" to channelId))
                .addOnSuccessListener { onSuccessListener() }
                .addOnFailureListener { onFailureListener() }

        } catch (ex: Exception) {
            handleException(ex)
        }
    }

    override fun addMember(
        groupId: String,
        list: List<Member>,
        onSuccessListener: () -> Unit,
        onFailureListener: () -> Unit
    ) {
       try {
           val now = Calendar.getInstance().time.toString()
           list.forEach {
                   member ->
               member.userId?.let {id ->
                   _userReference.document(id)
                       .collection(Constant.GROUP_LIST_NODE).document(groupId)
                       .set(mapOf("groupId" to groupId))
                   _memberListManager.addMember(
                       listId = groupId,
                       memberId = id,
                       timeStamp = member.joinedAt?: now,
                       onSuccessListener = {},
                       onFailureListener = onFailureListener
                   )
               }
           }
           onSuccessListener()
       } catch (ex: Exception) {
           handleException(ex)
       }
    }

    override fun addMember(
        groupId: String,
        memberId: String,
        onSuccessListener: () -> Unit,
        onFailureListener: () -> Unit
    ) {
        try {
            val now =Calendar.getInstance().time.toString()
            _userReference.document(memberId)
                .collection(Constant.GROUP_LIST_NODE).document(groupId)
                .set(mapOf("groupId" to groupId))
            _memberListManager.addMember(
                listId = groupId,
                memberId = memberId,
                timeStamp = now,
                onSuccessListener = onSuccessListener,
                onFailureListener = onFailureListener
            )
        } catch (ex: Exception) {
            handleException(ex)
        }
    }

    override fun removeMember(
        groupId: String,
        memberId: String,
        onSuccessListener: () -> Unit,
        onFailureListener: () -> Unit
    ) {
        try {
            _memberListManager.removeMemember(
                listId = groupId,
                memberId = memberId,
                onSuccessListener = {},
                onFailureListener = onFailureListener
            )
            _groupReference.document(groupId)
                .collection(Constant.PRIVATE_MEMBER_LIST_NODE).get()
                .addOnSuccessListener {
                    for (doc in it.documents)
                        _memberListManager.removeMememberPrivate(
                            listId = doc.id,
                            memberId = memberId,
                            onSuccessListener = {},
                            onFailureListener = onFailureListener
                        )
                }
                .addOnFailureListener { onFailureListener() }

            _userReference.document(memberId)
                .collection(Constant.GROUP_LIST_NODE).document(groupId).delete()
                .addOnSuccessListener { onSuccessListener() }
                .addOnFailureListener { onFailureListener() }
        } catch (ex: Exception) {
            handleException(ex)
        }
    }

    override fun depopulateMemberList(addOnCompleteListener: () -> Unit) {
        _memberListManager.depopulateMemberList(addOnCompleteListener = addOnCompleteListener)
    }
}