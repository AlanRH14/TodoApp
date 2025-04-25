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
import com.example.todoapp.presentation.viewmodel.SharedViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.List(),
    ) {
        composable<Screen.List>{ navBackStackEntry ->
            val mAction = navBackStackEntry.toRoute<Screen.List>().action
            ListScreen(
                mAction = mAction,
                sharedViewModel = sharedViewModel,
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
                sharedViewModel = sharedViewModel,
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