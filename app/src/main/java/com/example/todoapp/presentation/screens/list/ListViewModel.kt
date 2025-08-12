package com.example.todoapp.presentation.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.local.database.entities.ToDoTaskEntity
import com.example.todoapp.data.local.preferences.ConstantsPreferences
import com.example.todoapp.data.model.Priority
import com.example.todoapp.domain.repository.DataStoreRepository
import com.example.todoapp.domain.repository.ToDoRepository
import com.example.todoapp.util.Action
import com.example.todoapp.util.Constants.MAX_TITLE_LENGTH
import com.example.todoapp.util.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
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

            is ListUIEvent.OnSortTasksClicked -> getTasks(priority = event.priority)
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

    private fun updateTaskFields(selectedTask: ToDoTaskEntity?) {
        if (selectedTask != null) {
            _state.update {
                it.copy(
                    idTask = selectedTask.id,
                    titleTask = selectedTask.title,
                    description = selectedTask.description,
                    priority = selectedTask.priority
                )
            }
        } else {
            _state.update {
                it.copy(
                    idTask = 0,
                    titleTask = "",
                    description = "",
                    priority = Priority.LOW
                )
            }
        }
    }

    private fun getSelectedTask(taskID: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getSelectedTask(taskId = taskID).collect { task ->
                _state.update { it.copy(taskSelected = task) }
            }
        }
    }

    private fun setSearchAppBarState() {
        _state.update { it.copy() }
    }

    private fun onSearchBarUpdate(searchBar: String) {
        _state.update { it.copy(searchBarQuery = searchBar) }
    }

    private fun onTitleUpdate(title: String) {
        if (title.length < MAX_TITLE_LENGTH) {
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

    private fun saveSortState(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveState(
                key = ConstantsPreferences.PriorityPreferences,
                value = priority.name
            )
        }
    }

    private fun readSortState() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.readSate(key = ConstantsPreferences.PriorityPreferences)
                .map { Priority.valueOf(it) }
                .collect { priority ->
                    _state.update { it.copy(sortState = priority) }
                }
        }
    }
}
