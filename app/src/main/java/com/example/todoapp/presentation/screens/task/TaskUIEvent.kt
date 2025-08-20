package com.example.todoapp.presentation.screens.task

import com.example.todoapp.data.model.Priority
import com.example.todoapp.domain.ToDoTask
import com.example.todoapp.util.Action

interface TaskUIEvent {
    data class OnSnackBarActionClicked(val action: Action) : TaskUIEvent
    data class OnGetTaskSelected(val taskID: Int) : TaskUIEvent
    data class OnTaskFieldsUpdate(val taskSelected: ToDoTask?) : TaskUIEvent
    data class OnNavigateToListScreen(val action: Action) : TaskUIEvent
    data class OnTaskTitleUpdate(val taskTitle: String) : TaskUIEvent
    data class OnDescriptionUpdate(val description: String): TaskUIEvent
    data class OnPriorityUpdate(val priority: Priority): TaskUIEvent
}