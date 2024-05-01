package com.example.se114.di

import com.example.se114.data.repository.FirebaseChannelManager
import com.example.se114.data.repository.FirebaseMemberListManager
import com.example.se114.domain.repository.IChannel
import com.example.se114.domain.repository.IListMember
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    @Singleton
    abstract fun bindChannelManager(
        chanelManager: FirebaseChannelManager
    ) : IChannel

    @Binds
    @Singleton
    abstract fun bindListMemberManager(
        memberManager: FirebaseMemberListManager
    ) : IListMember
}