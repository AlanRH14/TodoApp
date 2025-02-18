package com.example.todoapp.navigation

import com.example.todoapp.util.Constants.LIST_SCREEN
import com.example.todoapp.util.Constants.TASK_SCREEN

sealed class Screen(val route: String) {
    data object LIST : Screen(route = LIST_SCREEN)

    data object TASK : Screen(route = TASK_SCREEN)
}