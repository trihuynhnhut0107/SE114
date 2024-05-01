package com.example.se114.domain.repository

interface IMessenger {

    fun sendMessage(senderId: String, messenger: String)
    fun listMessage(chatId: String)
}