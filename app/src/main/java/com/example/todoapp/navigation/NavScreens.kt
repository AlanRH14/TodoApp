package com.example.todoapp.navigation

import androidx.navigation.NavController
import com.example.todoapp.util.Action
import com.example.todoapp.util.Constants.LIST_SCREEN

class NavScreens(navController: NavController) {

    val list: (Action) -> Unit = { action ->
        navController.navigate("list/${action.name}") {
            popUpTo(LIST_SCREEN) { inclusive = true }
        }
    }
    val task: (Int) -> Unit = { taskId ->
        navController.navigate("task/$taskId")
    }
}