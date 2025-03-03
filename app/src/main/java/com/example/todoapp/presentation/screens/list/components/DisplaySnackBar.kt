package com.example.todoapp.presentation.screens.list.components

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.example.todoapp.util.Action
import kotlinx.coroutines.launch

@Composable
fun DisplaySnackBar(
    scaffoldState: SnackbarHostState,
    onUndoClicked: (Action) -> Unit,
    taskTitle: String,
    action: Action
) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult = scaffoldState.showSnackbar(
                    "${action.name}: $taskTitle",
                    actionLabel = setActionLabel(action = action)
                )

                undoDeletedTask(
                    action = action,
                    snackBarResult = snackBarResult,
                    onUndoClicked = onUndoClicked
                )
            }
        }
    }
}

private fun setActionLabel(action: Action): String {
    return if (action.name == "DELETE") {
        "UNDO"
    } else {
        "OK"
    }
}

private fun undoDeletedTask(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit,
) {
    if (snackBarResult == SnackbarResult.ActionPerformed &&
        action == Action.DELETE
    ) {
        onUndoClicked(Action.UNDO)
    }
}