package com.example.todoapp.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todoapp.presentation.screens.list.ListScreen
import com.example.todoapp.presentation.screens.splash.SplashScreen
import com.example.todoapp.presentation.screens.task.TaskScreen
import com.example.todoapp.presentation.viewmodel.SharedViewModel
import com.example.todoapp.util.Constants.LIST_ARGUMENT_KEY
import com.example.todoapp.util.Constants.LIST_SCREEN
import com.example.todoapp.util.Constants.SPLASH_SCREEN
import com.example.todoapp.util.Constants.TASK_ARGUMENT_KEY
import com.example.todoapp.util.Constants.TASK_SCREEN
import com.example.todoapp.util.toAction

@Composable
fun NavGraph(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    NavHost(
        navController = navController,
        startDestination = LIST_SCREEN,
    ) {
        /*composable(
            route = SPLASH_SCREEN,
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { -it },
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )
            },
        ) {
            SplashScreen(
                navigateToListScreen = screen.splash
            )
        }*/

        composable(
            route = LIST_SCREEN,
            arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
                type = NavType.StringType
            })
        ) { navBackStackEntry ->
            val mAction = navBackStackEntry.arguments?.getString(LIST_ARGUMENT_KEY).toAction()
            ListScreen(
                mAction = mAction,
                sharedViewModel = sharedViewModel,
                navigateToTaskScreen = { taskId ->
                    navController.navigate(Screen.Task(taskId = taskId))
                },
            )
        }

        composable(
            route = TASK_SCREEN,
            arguments = listOf(navArgument(TASK_ARGUMENT_KEY) {
                type = NavType.IntType
            }),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )
            }
        ) { navBackStackEntry ->
            val taskId = navBackStackEntry.arguments?.getInt(TASK_ARGUMENT_KEY)
            TaskScreen(
                sharedViewModel = sharedViewModel,
                taskId = taskId,
                navigateToListScreen = { action ->
                    navController.navigate(Screen.List(action = action)) {
                        popUpTo(Screen.Task) {}
                    }
                }
            )
        }
    }
}