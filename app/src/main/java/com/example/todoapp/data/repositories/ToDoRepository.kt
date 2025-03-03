package com.example.todoapp.data.repositories

import com.example.todoapp.data.ToDoDao
import com.example.todoapp.data.model.ToDoTask
import com.example.todoapp.util.RequestState
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

@ViewModelScoped
class ToDoRepository @Inject constructor(private val toDoDao: ToDoDao) {
    val sortByLowPriority: Flow<List<ToDoTask>> = toDoDao.sortByLowPriority()
    val sortByHighPriority: Flow<List<ToDoTask>> = toDoDao.sortByHighPriority()

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

    suspend fun deleteTask(task: ToDoTask) {
        toDoDao.deleteTask(toDoTask = task)
    }

    suspend fun deleteAllTasks() {
        toDoDao.deleteAllTasks()
    }

    suspend fun searchTask(searchQuery: String): Flow<List<ToDoTask>> {
        return toDoDao.searchDatabase(searchQuery = searchQuery)
    }
}