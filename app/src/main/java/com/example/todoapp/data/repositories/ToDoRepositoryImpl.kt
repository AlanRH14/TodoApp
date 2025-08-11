package com.example.todoapp.data.repositories

import com.example.todoapp.data.ToDoDao
import com.example.todoapp.data.model.ToDoTask
import com.example.todoapp.util.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ToDoRepositoryImpl(private val toDoDao: ToDoDao) {
    fun sortByLowPriority(): Flow<RequestState<List<ToDoTask>>> = flow {
        emit(RequestState.Loading)
        try {
            toDoDao.sortByLowPriority().collect {
                emit(RequestState.Success(it))
            }
        } catch (e: Exception) {
            emit(RequestState.Error(e))
        }
    }

    fun sortByHighPriority(): Flow<RequestState<List<ToDoTask>>> = flow {
        emit(RequestState.Loading)
        try {
            toDoDao.sortByHighPriority().collect {
                emit(RequestState.Success(it))
            }
        } catch (e: Exception) {
            emit(RequestState.Error(e))
        }
    }

    fun getAllTasks(): Flow<RequestState<List<ToDoTask>>> = flow {
        emit(RequestState.Loading)
        try {
            toDoDao.getAllTasks().collect {
                emit(RequestState.Success(it))
            }
        } catch (e: Exception) {
            emit(RequestState.Error(e))
        }
    }

    fun getSelectedTask(taskId: Int): Flow<ToDoTask> {
        return toDoDao.getSelectedTask(taskId = taskId)
    }

    suspend fun addTask(toDoTask: ToDoTask) {
        toDoDao.addTask(toDoTask = toDoTask)
    }

    suspend fun updateTask(toDoTask: ToDoTask) {
        toDoDao.updateTask(toDoTask = toDoTask)
    }

    suspend fun deleteTask(toDoTask: ToDoTask) {
        toDoDao.deleteTask(toDoTask = toDoTask)
    }

    suspend fun deleteAllTasks() {
        toDoDao.deleteAllTasks()
    }

    fun searchTask(searchQuery: String): Flow<RequestState<List<ToDoTask>>> = flow {
        emit(RequestState.Loading)
        try {
            toDoDao.searchDatabase(searchQuery = searchQuery).collect {
                emit(RequestState.Success(it))
            }
        } catch (e: Exception) {
            emit(RequestState.Error(e))
        }
    }
}