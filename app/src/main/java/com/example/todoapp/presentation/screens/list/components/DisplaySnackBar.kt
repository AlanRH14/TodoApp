package com.example.todoapp.presentation.screens.list.components

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.example.todoapp.util.Action
import kotlinx.coroutines.launch

@Composable
fun DisplaySnackBar(
    scaffoldState: SnackbarHostState,
    handleDatabaseAction: () -> Unit,
    taskTitle: String,
    action: Action
) {
    handleDatabaseAction()

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult = scaffoldState.showSnackbar(
                    "${action.name}: $taskTitle",
                    actionLabel = "Ok"
                )
            }
        }
    }
}