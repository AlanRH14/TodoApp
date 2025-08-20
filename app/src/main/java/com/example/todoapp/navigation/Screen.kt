package com.example.todoapp.navigation

import com.example.todoapp.util.Action
import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
    @Serializable
    data class List(val action: Action = Action.NO_ACTION, val taskID: Int = -1) : Screen

    @Serializable
    data class Task(val taskId: Int = -1) : Screen
}