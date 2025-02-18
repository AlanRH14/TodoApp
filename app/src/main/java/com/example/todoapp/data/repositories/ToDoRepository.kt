package com.example.todoapp.data.repositories

import com.example.todoapp.data.ToDoDao
import com.example.todoapp.data.model.ToDoTask
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ToDoRepository @Inject constructor(private val toDoDao: ToDoDao) {

    val getAllTasks: Flow<List<ToDoTask>> = toDoDao.getAllTasks()
    val sortByLowPriority: Flow<List<ToDoTask>> = toDoDao.sortByLowPriority()
    val sortByHighPriority: Flow<List<ToDoTask>> = toDoDao.sortByHighPriority()

    fun getSelectedTask(taskId: Int): Flow<ToDoTask> {
        return toDoDao.getSelectedTask(taskId = taskId)
    }

    suspend fun addTask(task: ToDoTask) {
        toDoDao.addTask(toDoTask = task)
    }

    suspend fun updateTask(task: ToDoTask) {
        toDoDao.updateTask(toDoTask = task)
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