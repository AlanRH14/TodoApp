package com.example.todoapp.domain.repository

import com.example.todoapp.data.local.database.entities.ToDoTaskEntity
import com.example.todoapp.data.model.Priority
import com.example.todoapp.util.RequestState
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {
    fun getTasksByPriority(sortTasks: Priority): Flow<RequestState<List<ToDoTaskEntity>>>

    fun getSelectedTask(taskId: Int): Flow<ToDoTaskEntity>

    suspend fun addTask(toDoTaskEntity: ToDoTaskEntity)

    suspend fun updateTask(toDoTaskEntity: ToDoTaskEntity)

    suspend fun deleteTask(toDoTaskEntity: ToDoTaskEntity)

    suspend fun deleteAllTasks()

    fun searchTask(searchQuery: String): Flow<RequestState<List<ToDoTaskEntity>>>
}