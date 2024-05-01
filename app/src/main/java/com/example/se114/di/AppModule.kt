package com.example.se114.di

import android.app.Application
import com.example.se114.data.repository.FirebaseAuthenticator
import com.example.se114.data.repository.FirebaseChannelManager
import com.example.se114.data.repository.FirebaseGroupManager
import com.example.se114.data.repository.FirebaseMemberListManager
import com.example.se114.domain.repository.IAuthenticator
import com.example.se114.domain.repository.IChannel
import com.example.se114.domain.repository.IGroup
import com.example.se114.domain.repository.IListMember
import com.example.se114.domain.repository.IUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuthInstance() : FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    @Named("firebase-auth")
    fun provideFirebaseAuth(auth: FirebaseAuth) : IAuthenticator {
        return FirebaseAuthenticator(auth)
    }

    @Provides
    @Singleton
    fun provideFirebaseFireStoreInstance(): FirebaseFirestore= Firebase.firestore

    @Provides
    @Singleton
    @Named("firebase-member")
    fun provideFirebaseMemberListStore(database: FirebaseFirestore) : IListMember {
        return FirebaseMemberListManager(database)
    }
    @Provides
    @Singleton
    @Named("firebase-channel")
    fun provideFirebaseChannelStore(database: FirebaseFirestore, memberManager: IListMember) : IChannel {
        return FirebaseChannelManager(database, memberManager)
    }

    @Provides
    @Singleton
    @Named("firebase-group")
    fun provideFirebaseGroupStore(database: FirebaseFirestore, channelManager: IChannel, memberManager: IListMember) : IGroup {
        return FirebaseGroupManager(database, channelManager, memberManager)
    }

    @Provides
    @Singleton
    fun provideFirebaseStorageInstance() : FirebaseStorage = Firebase.storage
}