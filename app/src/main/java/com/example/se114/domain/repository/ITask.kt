package com.example.se114.domain.repository

interface ITask {

    fun createNewTask(userId: String)
    fun createAssignment(taskId: String, involement: List<String>)
    fun deleteAssignment(taskId: String)
    fun completeTask(taskId: String)
    fun reverseTask(taskId: String)

}