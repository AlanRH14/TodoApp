package com.example.todoapp.navigation

import com.example.todoapp.util.Action
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data class List (val action: Action = Action.NO_ACTION): Screen()

    @Serializable
    data class Task(val taskId: Int): Screen()
}