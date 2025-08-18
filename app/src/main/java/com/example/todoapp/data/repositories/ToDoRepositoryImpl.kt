package com.example.todoapp.data.repositories

import android.util.Log
import com.example.todoapp.common.GenericMapper
import com.example.todoapp.data.local.database.dao.ToDoDao
import com.example.todoapp.data.local.database.entities.ToDoTaskEntity
import com.example.todoapp.data.model.Priority
import com.example.todoapp.domain.ToDoTask
import com.example.todoapp.domain.repository.ToDoRepository
import com.example.todoapp.util.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ToDoRepositoryImpl(
    private val toDoDao: ToDoDao,
    private val entityMapper: GenericMapper<ToDoTaskEntity, ToDoTask>,
    private val domainMapper: GenericMapper<ToDoTask, ToDoTaskEntity>
) : ToDoRepository {
    override fun sortByLowPriority(): Flow<RequestState<List<ToDoTask>>> = flow {
        emit(RequestState.Loading)
        try {
            toDoDao.sortByLowPriority().collect {
                emit(RequestState.Success(it.map { task ->
                    entityMapper.mapToDomain(task)
                }))
            }
        } catch (e: Exception) {
            emit(RequestState.Error(e))
        }
    }

    override fun sortByHighPriority(): Flow<RequestState<List<ToDoTask>>> = flow {
        emit(RequestState.Loading)
        try {
            toDoDao.sortByHighPriority().collect {
                emit(RequestState.Success(it.map { task ->
                    entityMapper.mapToDomain(task)
                }))
            }
        } catch (e: Exception) {
            emit(RequestState.Error(e))
        }
    }

    override fun getAllTasks(): Flow<RequestState<List<ToDoTask>>> = flow {
        emit(RequestState.Loading)
        try {
            toDoDao.getAllTasks().collect {
                emit(RequestState.Success(it.map { task ->
                    entityMapper.mapToDomain(task)
                }))
            }
        } catch (e: Exception) {
            emit(RequestState.Error(e))
        }
    }

    override fun getTasksByPriority(sortTasks: Priority): Flow<RequestState<List<ToDoTask>>> =
        flow {
            emit(RequestState.Loading)
            try {
                val tasks = when (sortTasks) {
                    Priority.LOW -> toDoDao.sortByLowPriority()

                    Priority.HIGH -> toDoDao.sortByHighPriority()

                    else -> toDoDao.getAllTasks()
                }

                tasks.collect {
                    emit(RequestState.Success(it.map { task ->
                        entityMapper.mapToDomain(task)
                    }))
                }
            } catch (e: Exception) {
                emit(RequestState.Error(e))
            }
        }

    override fun getSelectedTask(taskId: Int): Flow<ToDoTask> = flow {
        try {
            toDoDao.getSelectedTask(taskId = taskId).collect {
                emit(entityMapper.mapToDomain(it))
            }
        } catch (e: Exception) {
            Log.d("LordMiau", "Error: ${e.message} ID: $taskId")
        }
    }

    override suspend fun addTask(toDoTask: ToDoTask) {
        toDoDao.addTask(toDoTaskEntity = domainMapper.mapToDomain(toDoTask))
    }

    override suspend fun updateTask(toDoTask: ToDoTask) {
        toDoDao.updateTask(toDoTaskEntity = domainMapper.mapToDomain(toDoTask))
    }

    override suspend fun deleteTask(toDoTask: ToDoTask) {
        toDoDao.deleteTask(toDoTaskEntity = domainMapper.mapToDomain(toDoTask))
    }

    override suspend fun deleteAllTasks() {
        toDoDao.deleteAllTasks()
    }

    override fun searchTask(searchQuery: String): Flow<RequestState<List<ToDoTask>>> = flow {
        emit(RequestState.Loading)
        try {
            toDoDao.searchDatabase(searchQuery = searchQuery).collect {
                emit(RequestState.Success(it.map { task -> entityMapper.mapToDomain(task) }))
            }
        } catch (e: Exception) {
            emit(RequestState.Error(e))
        }
    }
}