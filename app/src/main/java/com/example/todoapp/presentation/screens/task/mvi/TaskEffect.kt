package com.example.todoapp.presentation.screens.task.mvi

import com.example.todoapp.util.Action

interface TaskEffect {
    data class NavigateToListScreen(val action: Action, val taskID: Int) : TaskEffect
    data class ShowMessage(val message: String) : TaskEffect
}