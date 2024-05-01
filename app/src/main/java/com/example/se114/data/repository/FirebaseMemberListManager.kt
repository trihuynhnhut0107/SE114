package com.example.se114.data.repository

import androidx.compose.runtime.mutableStateOf
import com.example.se114.data.model.Member
import com.example.se114.domain.repository.IListMember
import com.example.se114.utils.Constant
import com.example.se114.utils.handleException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObject
import java.util.Calendar
import javax.inject.Inject

class FirebaseMemberListManager @Inject constructor(
    private val _database: FirebaseFirestore
): IListMember {
    private val _listMemberReference = _database.collection(Constant.MEMBER_LIST_NODE)
    private val _privateListMemberReference = _database.collection(Constant.PRIVATE_MEMBER_LIST_NODE)


    override val members = mutableStateOf<List<Member>>(listOf())

    private var currentMemberListListener: ListenerRegistration?= null

    override fun createNewPublicList(
        memberId: String,
        onSuccessListener: () -> Unit,
        onFailureListener: () -> Unit
    ): String {
        val id = _listMemberReference.document().id

        _listMemberReference.document(id).set(mapOf("id" to id))
        _listMemberReference.document(id)
            .collection(Constant.MEMBER_NODE).document(memberId)
            .set(Member(
                userId = memberId,
                joinedAt = Calendar.getInstance().time.toString()
            ))
            .addOnSuccessListener { onSuccessListener() }
            .addOnFailureListener { onFailureListener() }

        return id
    }

    override fun createNewPrivateList(
        memberId: String,
        onSuccessListener: () -> Unit,
        onFailureListener: () -> Unit
    ): String {
        val id = _privateListMemberReference.document().id

        _privateListMemberReference.document(id).set(mapOf("id" to id))
        _privateListMemberReference.document(id)
            .collection(Constant.MEMBER_NODE).document(memberId)
            .set(Member(
                userId = memberId,
                joinedAt = Calendar.getInstance().time.toString()
            ))

        return id
    }

    override fun addMember(
        listId: String,
        memberId: String,
        timeStamp: String?,
        onSuccessListener: () -> Unit,
        onFailureListener: () -> Unit
    ) {
        try {
            val joinedAt = timeStamp?: Calendar.getInstance().time.toString()
            _listMemberReference.document(listId)
                .collection(Constant.MEMBER_NODE).document(memberId)
                .set(Member(
                    userId = memberId,
                    joinedAt = joinedAt
                ))
                .addOnSuccessListener { onSuccessListener() }
                .addOnFailureListener { onFailureListener() }
        } catch (ex: Exception) {
            handleException(ex)
        }
    }

    override fun addMemberPrivate(
        listId: String,
        memberId: String,
        timeStamp: String?,
        onSuccessListener: () -> Unit,
        onFailureListener: () -> Unit
    ) {
        try {
            val joinedAt = timeStamp?:Calendar.getInstance().time.toString()
            _privateListMemberReference.document(listId)
                .collection(Constant.MEMBER_NODE).document(memberId)
                .set(Member(
                    userId = memberId,
                    joinedAt = joinedAt
                ))
                .addOnSuccessListener { onSuccessListener() }
                .addOnFailureListener { onFailureListener() }
        } catch (ex: Exception) {
            handleException(ex)
        }
    }

    override fun removeMemember(
        listId: String,
        memberId: String,
        onSuccessListener: () -> Unit,
        onFailureListener: () -> Unit
    ) {
       try {
           _listMemberReference.document(listId)
               .collection(Constant.MEMBER_NODE).document(memberId).delete()
               .addOnSuccessListener {
                   onSuccessListener()
               }
               .addOnFailureListener {
                   onFailureListener()
                   handleException(it)
               }
       } catch (ex: Exception) {
           handleException(ex)
       }
    }

    override fun removeMememberPrivate(
        listId: String,
        memberId: String,
        onSuccessListener: () -> Unit,
        onFailureListener: () -> Unit
    ) {
        try {
            _privateListMemberReference.document(listId)
                .collection(Constant.MEMBER_NODE).document(memberId).delete()
                .addOnSuccessListener {
                }
                .addOnFailureListener {
                    handleException(it)
                }
        } catch (ex: Exception) {
            handleException(ex)
        }
    }

    override fun retrieveMemberList(listId: String, addOnCompleteListener: () -> Unit) {
        try {
            _listMemberReference.document(listId)
                .collection(Constant.MEMBER_NODE)
                .addSnapshotListener { value, error ->
                    if (error != null)
                        handleException(error)

                    value?.let {
                        members.value = value.documents.mapNotNull { it.toObject<Member>() }
                    }
                    addOnCompleteListener()
                }
        } catch (ex: Exception) {
            handleException(ex)
        }
    }

    override fun retrieveMemberPrivateList(listId: String, addOnCompleteListener: () -> Unit) {
        try {
            _privateListMemberReference.document(listId)
                .collection(Constant.MEMBER_NODE)
                .addSnapshotListener { value, error ->
                    if (error != null)
                        handleException(error)

                    value?.let {
                        members.value = value.documents.mapNotNull { it.toObject<Member>() }
                    }
                    addOnCompleteListener()
                }
        } catch (ex: Exception) {
            handleException(ex)
        }
    }

    override fun removeList(
        listId: String,
        onSuccessListener: () -> Unit,
        onFailureListener: () -> Unit
    ) {
        try {
            _listMemberReference.document(listId).delete()
                .addOnSuccessListener { onSuccessListener() }
                .addOnFailureListener { onFailureListener() }
        } catch (ex: Exception) {
            handleException(ex)
        }
    }

    override fun removePrivateList(
        listId: String,
        onSuccessListener: () -> Unit,
        onFailureListener: () -> Unit
    ) {
        try {
            _privateListMemberReference.document(listId).delete()
                .addOnSuccessListener { onSuccessListener() }
                .addOnFailureListener { onFailureListener() }
        } catch (ex: Exception) {
            handleException(ex)
        }
    }

    override fun depopulateMemberList(addOnCompleteListener: () -> Unit) {
        members.value = listOf()
        currentMemberListListener = null
        addOnCompleteListener()
    }
}