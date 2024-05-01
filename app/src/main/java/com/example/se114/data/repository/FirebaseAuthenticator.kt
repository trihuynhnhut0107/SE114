package com.example.se114.data.repository

import android.util.Log
import com.example.se114.data.model.UserData
import com.example.se114.domain.repository.IAuthenticator
import com.example.se114.exception.CustomException
import com.example.se114.utils.handleException
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FirebaseAuthenticator @Inject constructor(
    private val _auth: FirebaseAuth,
): IAuthenticator {
    override fun signIn(
        email: String,
        password: String,
        onSuccessListener: () -> Unit,
        onFailureListener: () -> Unit
    ) {
        if(email.isEmpty() or password.isEmpty()) {
            handleException(CustomException.EmptyBlankException)
        } else {
            _auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        onSuccessListener()
                    }
                    else {
                        onFailureListener()
                        handleException(it.exception)
                    }
                }
        }
    }

    override fun signOut(onCompleteListener: () -> Unit) {
        _auth.signOut()
        onCompleteListener()
    }

    override fun signUp(
        email: String,
        password: String,
        onSuccessListener: () -> Unit,
        onFailureListener: () -> Unit
    ) {
        if(email.isEmpty() or password.isEmpty()) {
            handleException(CustomException.EmptyBlankException)
        } else {
            _auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener {
                    if (it.isSuccessful) onSuccessListener()
                    else {
                        onFailureListener()
                        handleException(it.exception)
                    }
                }
        }
    }

    override fun getCurrentUser(): UserData? {
        _auth.currentUser?.let {
            return UserData(userId = it.uid)
        }
        return null
    }
}