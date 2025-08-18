package com.example.todoapp.domain.repository

import com.example.todoapp.data.model.Priority
import com.example.todoapp.domain.ToDoTask
import com.example.todoapp.util.RequestState
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {
    fun sortByLowPriority(): Flow<RequestState<List<ToDoTask>>>

    fun sortByHighPriority(): Flow<RequestState<List<ToDoTask>>>

    fun getAllTasks(): Flow<RequestState<List<ToDoTask>>>

    fun getTasksByPriority(sortTasks: Priority): Flow<RequestState<List<ToDoTask>>>

    fun getSelectedTask(taskId: Int): Flow<ToDoTask>

    suspend fun addTask(toDoTask: ToDoTask)

    suspend fun updateTask(toDoTask: ToDoTask)

    suspend fun deleteTask(toDoTask: ToDoTask)

    suspend fun deleteAllTasks()

    fun searchTask(searchQuery: String): Flow<RequestState<List<ToDoTask>>>
}