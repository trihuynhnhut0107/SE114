package com.example.se114.data.repository

import androidx.compose.runtime.MutableState
import com.example.se114.data.model.Channel
import com.example.se114.data.model.Member
import com.example.se114.domain.repository.IChannel
import com.example.se114.domain.repository.IListMember
import com.example.se114.utils.Constant
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import java.util.Calendar
import javax.inject.Inject

class FirebaseChannelManager @Inject constructor(
    private val _database: FirebaseFirestore,
    private val _memberListManager: IListMember
): IChannel {
    private val _channelReference = _database.collection(Constant.CHANNEL_NODE)
    override val members: MutableState<List<Member>>
        get() = _memberListManager.members

    override fun createPublicChannel(
        creatorId: String,
        name: String,
        listId: String?,
        listMemberId: String?,
        onSuccessListener: () -> Unit,
        onFailureListener: () -> Unit
    ): String {
        var list = listId

        if (list == null) {
            list = _memberListManager.createNewPublicList(
                creatorId,
                onSuccessListener = {},
                onFailureListener = onFailureListener
            )
        }

        val channelId = listMemberId?: _channelReference.document().id
        val channel = Channel(
            channelId = channelId,
            channelName = name,
            creatorId = creatorId,
            listMemberId = list,
            createdAt = Calendar.getInstance().time.toString()
        )

        _channelReference.document(channelId).set(channel)
            .addOnSuccessListener { onSuccessListener() }
            .addOnFailureListener { onFailureListener() }
        return channelId
    }

    override fun createPrivateChannel(
        creatorId: String,
        name: String,
        listId: String?,
        onSuccessListener: () -> Unit,
        onFailureListener: () -> Unit
    ): String {
        var list = listId
        if (list == null) {
            list = _memberListManager.createNewPrivateList(
                creatorId,
                onSuccessListener = {},
                onFailureListener = onFailureListener
            )
        }

        val channelId = list
        val channel = Channel(
            channelId = channelId,
            channelName = name,
            creatorId = creatorId,
            listMemberId = list,
            createdAt = Calendar.getInstance().time.toString(),
            public = false
        )

        _channelReference.document(channelId).set(channel)
        return channelId
    }

    override fun retrievePublicMember(channelId: String, addOnCompleteListener: () -> Unit) {
        _memberListManager.retrieveMemberList(channelId, addOnCompleteListener = addOnCompleteListener)
    }

    override fun retrieveMemberPrivate(channelId: String, addOnCompleteListener: () -> Unit) {
        _memberListManager.retrieveMemberPrivateList(channelId, addOnCompleteListener = addOnCompleteListener)
    }

    override fun addMemberPrivateList(
        channelId: String,
        list: List<Member>,
        onSuccessListener: () -> Unit,
        onFailureListener: () -> Unit
    ) {
        list.forEach {member ->
            member.userId?.let {
                _memberListManager.addMemberPrivate(
                    listId = channelId,
                    memberId = it,
                    onSuccessListener = onSuccessListener,
                    onFailureListener = onFailureListener
                    )
            }
        }
    }

    override fun removeChannel(
        channelId: String,
        onSuccessListener: () -> Unit,
        onFailureListener: () -> Unit
    ) {
        _channelReference.document(channelId).get().addOnSuccessListener {
            if(it.exists()) {
                val tmp = it.toObject<Channel>()
                if (tmp?.public == false )
                    _memberListManager.removePrivateList(
                        channelId,
                        onSuccessListener = onSuccessListener,
                        onFailureListener = onFailureListener
                    )
            }
        }
        _channelReference.document(channelId).delete()
    }
}