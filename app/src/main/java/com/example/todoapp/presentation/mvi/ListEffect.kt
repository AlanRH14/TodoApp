package com.example.todoapp.presentation.mvi

import com.example.todoapp.util.Action

sealed interface ListEffect {
    data class NavigateToListScreen(val action: Action): ListEffect

    data class ShowMessage(val message: String): ListEffect
}