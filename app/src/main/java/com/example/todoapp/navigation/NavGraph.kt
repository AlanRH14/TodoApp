package com.example.todoapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todoapp.presentation.screens.list.ListScreen
import com.example.todoapp.presentation.screens.task.TaskScreen
import com.example.todoapp.presentation.viewmodel.SharedViewModel
import com.example.todoapp.util.Constants.LIST_ARGUMENT_KEY
import com.example.todoapp.util.Constants.TASK_ARGUMENT_KEY
import com.example.todoapp.util.toAction

@Composable
fun NavGraph(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    val screen = remember(navController) {
        NavScreens(navController = navController)
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
        ) { navBackStackEntry ->
            val mAction = navBackStackEntry.arguments?.getString(LIST_ARGUMENT_KEY).toAction()
            ListScreen(
                mAction = mAction,
                navigateToTaskScreen = screen.task,
                sharedViewModel = sharedViewModel,
            )
        }

        composable(
            route = Screen.TASK.route,
            arguments = listOf(navArgument(TASK_ARGUMENT_KEY) {
                type = NavType.IntType
            })
        ) { navBackStackEntry ->
            val taskId = navBackStackEntry.arguments?.getInt(TASK_ARGUMENT_KEY)
            TaskScreen(
                sharedViewModel = sharedViewModel,
                taskId = taskId,
                navigateToListScreen = screen.list
            )
        }
    }
}