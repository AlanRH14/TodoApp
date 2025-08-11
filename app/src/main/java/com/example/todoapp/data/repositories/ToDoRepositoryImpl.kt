package com.example.todoapp.data.repositories

import com.example.todoapp.data.local.database.dao.ToDoDao
import com.example.todoapp.data.local.database.ToDoTask
import com.example.todoapp.domain.repository.ToDoRepository
import com.example.todoapp.util.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ToDoRepositoryImpl(private val toDoDao: ToDoDao): ToDoRepository {
    override fun sortByLowPriority(): Flow<RequestState<List<ToDoTask>>> = flow {
        emit(RequestState.Loading)
        try {
            toDoDao.sortByLowPriority().collect {
                emit(RequestState.Success(it))
            }
        } catch (e: Exception) {
            emit(RequestState.Error(e))
        }
    }

    override fun sortByHighPriority(): Flow<RequestState<List<ToDoTask>>> = flow {
        emit(RequestState.Loading)
        try {
            toDoDao.sortByHighPriority().collect {
                emit(RequestState.Success(it))
            }
        } catch (e: Exception) {
            emit(RequestState.Error(e))
        }
    }

    override fun getAllTasks(): Flow<RequestState<List<ToDoTask>>> = flow {
        emit(RequestState.Loading)
        try {
            toDoDao.getAllTasks().collect {
                emit(RequestState.Success(it))
            }
        } catch (e: Exception) {
            emit(RequestState.Error(e))
        }
    }

    override fun getSelectedTask(taskId: Int): Flow<ToDoTask> {
        return toDoDao.getSelectedTask(taskId = taskId)
    }

    override suspend fun addTask(toDoTask: ToDoTask) {
        toDoDao.addTask(toDoTask = toDoTask)
    }

    override suspend fun updateTask(toDoTask: ToDoTask) {
        toDoDao.updateTask(toDoTask = toDoTask)
    }

    override suspend fun deleteTask(toDoTask: ToDoTask) {
        toDoDao.deleteTask(toDoTask = toDoTask)
    }

    override suspend fun deleteAllTasks() {
        toDoDao.deleteAllTasks()
    }

    override fun searchTask(searchQuery: String): Flow<RequestState<List<ToDoTask>>> = flow {
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