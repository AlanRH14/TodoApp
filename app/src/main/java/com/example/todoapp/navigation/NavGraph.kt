package com.example.todoapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todoapp.util.Constants.LIST_ARGUMENT_KEY
import com.example.todoapp.util.Constants.TASK_ARGUMENT_KEY

@Composable
fun NavGraph(
    navController: NavHostController,
) {
    val screen = remember(navController) {
        Screens(navController = navController)
    }

    NavHost(
        navController = navController,
        startDestination = Screen.LIST.route,
    ) {
        composable(
            route = Screen.LIST.route,
            arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
                type = NavType.StringType
            })
        ) {

        }

        composable(
            route = Screen.TASK.route,
            arguments = listOf(navArgument(TASK_ARGUMENT_KEY) {
                type = NavType.IntType
            })
        ) {

        }
    }
}