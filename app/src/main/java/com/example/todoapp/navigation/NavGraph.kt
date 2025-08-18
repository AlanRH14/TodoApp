package com.example.todoapp.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.todoapp.presentation.screens.list.ListScreen
import com.example.todoapp.presentation.screens.task.TaskScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.List(),
    ) {
        composable<Screen.List>{ navBackStackEntry ->
            val mAction = navBackStackEntry.toRoute<Screen.List>().action
            ListScreen(
                action = mAction,
                navigateToTaskScreen = { taskId ->
                    navController.navigate(Screen.Task(taskId = taskId))
                },
            )
        }

        composable<Screen.Task>(
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )
            }
        ) { navBackStackEntry ->
            val taskId = navBackStackEntry.toRoute<Screen.Task>().taskId
            TaskScreen(
                taskId = taskId,
                navigateToListScreen = { action ->
                    navController.navigate(Screen.List(action = action)) {
                        popUpTo(Screen.List()) { inclusive = true }
                    }
                }
            )
        }
    }
}