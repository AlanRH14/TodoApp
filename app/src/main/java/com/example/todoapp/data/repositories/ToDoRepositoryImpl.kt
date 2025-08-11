package com.example.todoapp.data.repositories

import com.example.todoapp.data.local.database.dao.ToDoDao
import com.example.todoapp.data.local.database.entities.ToDoTaskEntity
import com.example.todoapp.data.model.Priority
import com.example.todoapp.domain.repository.ToDoRepository
import com.example.todoapp.util.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

class ToDoRepositoryImpl(private val toDoDao: ToDoDao) : ToDoRepository {
    override fun getTasksByPriority(sortTasks: Priority): Flow<RequestState<List<ToDoTaskEntity>>> =
        flow {
            emit(RequestState.Loading)
            try {
                val tasks = when (sortTasks) {
                    Priority.LOW -> toDoDao.sortByLowPriority()

                    Priority.HIGH -> toDoDao.sortByHighPriority()

                    else -> toDoDao.getAllTasks()
                }

                tasks.collect {
                    emit(RequestState.Success(it))
                }
            } catch (e: Exception) {
                emit(RequestState.Error(e))
            }
        }

    override fun getSelectedTask(taskId: Int): Flow<ToDoTaskEntity> {
        return toDoDao.getSelectedTask(taskId = taskId)
    }

    override suspend fun addTask(toDoTaskEntity: ToDoTaskEntity) {
        toDoDao.addTask(toDoTaskEntity = toDoTaskEntity)
    }

    override suspend fun updateTask(toDoTaskEntity: ToDoTaskEntity) {
        toDoDao.updateTask(toDoTaskEntity = toDoTaskEntity)
    }

    override suspend fun deleteTask(toDoTaskEntity: ToDoTaskEntity) {
        toDoDao.deleteTask(toDoTaskEntity = toDoTaskEntity)
    }

    override suspend fun deleteAllTasks() {
        toDoDao.deleteAllTasks()
    }

    override fun searchTask(searchQuery: String): Flow<RequestState<List<ToDoTaskEntity>>> = flow {
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