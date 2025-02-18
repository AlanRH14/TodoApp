package com.example.todoapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController

@Composable
fun NavGraph(
    navController: NavHostController,
) {
    val screen = remember(navController) {
        Screens(navController = navController)
    }
}