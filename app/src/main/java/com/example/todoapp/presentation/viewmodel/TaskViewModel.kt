package com.example.todoapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.model.Priority
import com.example.todoapp.domain.ToDoTask
import com.example.todoapp.domain.repository.ToDoRepository
import com.example.todoapp.presentation.screens.list.mvi.ListState
import com.example.todoapp.presentation.screens.task.mvi.TaskEffect
import com.example.todoapp.presentation.screens.task.mvi.TaskState
import com.example.todoapp.presentation.screens.task.mvi.TaskUIEvent
import com.example.todoapp.util.Action
import com.example.todoapp.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: ToDoRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(TaskState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<TaskEffect>()
    val effect = _effect.asSharedFlow()

    fun onEvent(event: TaskUIEvent) {
        when (event) {
            is TaskUIEvent.OnSnackBarActionClicked -> {
                onActionUpdate(action = event.action)
                handleDatabaseActions(action = event.action)
            }

            is TaskUIEvent.OnGetTaskSelected -> getSelectedTask(taskID = event.taskID)
            is TaskUIEvent.OnTaskFieldsUpdate -> updateTaskFields(taskSelected = event.taskSelected)
            is TaskUIEvent.OnNavigateToListScreen -> navigateToListScreen(
                action = event.action,
                taskID = event.taskID
            )
            is TaskUIEvent.OnTaskTitleUpdate -> onTitleUpdate(title = event.taskTitle)
            is TaskUIEvent.OnDescriptionUpdate -> onDescriptionUpdate(event.description)
            is TaskUIEvent.OnPriorityUpdate -> onPriorityUpdate(priority = event.priority)
        }
    }

    private fun handleDatabaseActions(action: Action) {
        when (action) {
            Action.ADD -> addTask(
                title = _state.value.titleTask,
                description = _state.value.description,
                priority = _state.value.priority
            )

            Action.UPDATE -> updateTask(
                id = _state.value.taskID,
                title = _state.value.titleTask,
                description = _state.value.description,
                priority = _state.value.priority
            )

            Action.DELETE -> deleteTask(
                id = _state.value.taskID,
                title = _state.value.titleTask,
                description = _state.value.description,
                priority = _state.value.priority
            )

            else -> Unit
        }
    }

    private fun addTask(
        title: String,
        description: String,
        priority: Priority
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                title = title,
                description = description,
                priority = priority
            )

            repository.addTask(toDoTask = toDoTask)
        }
    }

    private fun updateTask(
        id: Int,
        title: String,
        description: String,
        priority: Priority
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                id = id,
                title = title,
                description = description,
                priority = priority
            )

            repository.updateTask(toDoTask = toDoTask)
        }
    }

    private fun deleteTask(
        id: Int,
        title: String,
        description: String,
        priority: Priority
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                id = id,
                title = title,
                description = description,
                priority = priority,
            )

            repository.deleteTask(toDoTask = toDoTask)
        }
    }

    private fun updateTaskFields(taskSelected: ToDoTask?) {
        if (taskSelected != null) {
            _state.update {
                it.copy(
                    taskID = taskSelected.id,
                    titleTask = taskSelected.title,
                    description = taskSelected.description,
                    priority = taskSelected.priority,
                )
            }
        } else {
            _state.update {
                it.copy(
                    taskID = 0,
                    titleTask = "",
                    description = "",
                    priority = Priority.NONE,
                )
            }
        }
    }

    private fun getSelectedTask(taskID: Int) {
        if (taskID > -1) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getSelectedTask(taskId = taskID).collect { task ->
                    _state.update { it.copy(taskSelected = task) }
                }
            }
        }
    }

    private fun onTitleUpdate(title: String) {
        if (title.length < Constants.MAX_TITLE_LENGTH) {
            _state.update { it.copy(titleTask = title) }
        }
    }

    private fun onDescriptionUpdate(description: String) {
        _state.update { it.copy(description = description) }
    }

    private fun onPriorityUpdate(priority: Priority) {
        _state.update { it.copy(priority = priority) }
    }

    private fun validateFields(): Boolean {
        return _state.value.titleTask.isNotEmpty() && _state.value.description.isNotEmpty()
    }

    private fun onActionUpdate(action: Action) {
        _state.update { it.copy(action = action) }
    }

    private fun navigateToListScreen(action: Action, taskID: Int) {
        viewModelScope.launch {
            if (action == Action.NO_ACTION) {
                handleDatabaseActions(action = action)
                _effect.emit(TaskEffect.NavigateToListScreen(action = action, taskID = taskID))
            } else {
                if (validateFields()) {
                    handleDatabaseActions(action = action)
                    _effect.emit(TaskEffect.NavigateToListScreen(action = action, taskID = taskID))
                } else {
                    _effect.emit(TaskEffect.ShowMessage(message = "Fields Empty."))
                }
            }
        }
    }
}