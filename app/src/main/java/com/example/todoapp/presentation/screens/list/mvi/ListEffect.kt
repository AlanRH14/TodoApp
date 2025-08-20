package com.example.todoapp.presentation.screens.list.mvi

sealed interface ListEffect {
    data class NavigateToTaskScreen(val taskID: Int) : ListEffect

    data class ShowMessage(val message: String) : ListEffect
}