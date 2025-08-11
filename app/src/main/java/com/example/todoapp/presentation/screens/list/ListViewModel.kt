package com.example.todoapp.presentation.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.local.database.entities.ToDoTaskEntity
import com.example.todoapp.data.model.Priority
import com.example.todoapp.domain.repository.DataStoreRepository
import com.example.todoapp.domain.repository.ToDoRepository
import com.example.todoapp.util.Action
import com.example.todoapp.util.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListViewModel(
    private val repository: ToDoRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ListState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ListEffect>()
    val effect = _effect.asSharedFlow()

    fun onEvent(event: ListUIEvent) {
        when (event) {
            is ListUIEvent.GetTasks -> getTasks(priority = event.priority)

            is ListUIEvent.OnClickActionSnackBar -> {}
        }
    }

    private fun getTasks(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTasksByPriority(sortTasks = priority).collect { tasks ->
                when (tasks) {
                    is RequestState.Success -> {
                        _state.update { it.copy(tasks = tasks.data) }
                    }

                    else -> Unit
                }
            }
        }
    }


    private fun handleDatabaseActions(
        action: Action,
        id: Int,
        title: String,
        description: String,
        priority: Priority
    ) {
        when (action) {
            Action.ADD -> addTask(title = title, description = description, priority = priority)

            Action.UPDATE -> updateTask(
                id = id,
                title = title,
                description = description,
                priority = priority
            )

            Action.DELETE -> deleteTask(
                id = id,
                title = title,
                description = description,
                priority = priority
            )

            Action.DELETE_ALL -> deleteAllTask()

            Action.UNDO -> addTask(title = title, description = description, priority = priority)

            else -> Unit
        }
    }

    private fun addTask(
        title: String,
        description: String,
        priority: Priority
    ) {
        viewModelScope.launch {
            val toDoTask = ToDoTaskEntity(
                title = title,
                description = description,
                priority = priority
            )

            repository.addTask(toDoTaskEntity = toDoTask)
        }
    }

    private fun updateTask(
        id: Int,
        title: String,
        description: String,
        priority: Priority
    ) {
        viewModelScope.launch {
            val toDoTask = ToDoTaskEntity(
                id = id,
                title = title,
                description = description,
                priority = priority
            )

            repository.updateTask(toDoTaskEntity = toDoTask)
        }
    }

    private fun deleteTask(
        id: Int,
        title: String,
        description: String,
        priority: Priority
    ) {
        viewModelScope.launch {
            val toDoTask = ToDoTaskEntity(
                id = id,
                title = title,
                description = description,
                priority = priority,
            )

            repository.deleteTask(toDoTaskEntity = toDoTask)
        }
    }

    private fun deleteAllTask() {
        viewModelScope.launch {
            repository.deleteAllTasks()
        }
    }
}