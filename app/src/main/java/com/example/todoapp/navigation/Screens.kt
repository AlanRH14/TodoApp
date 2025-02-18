package com.example.todoapp.navigation

import androidx.navigation.NavController
import com.example.todoapp.util.Action

class Screens(navController: NavController) {

    val list: (Action) -> Unit = { action ->
        navController.navigate("list/${action.name}") {
            popUpTo("LIST_SCREEN") { inclusive = true }
        }
    }
    val task: (Int) -> Unit = { taskId ->
        navController.navigate("task/$taskId")
    }
}