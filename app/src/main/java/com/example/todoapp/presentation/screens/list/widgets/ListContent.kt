package com.example.todoapp.presentation.screens.list.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.todoapp.data.model.Priority
import com.example.todoapp.data.model.ToDoTask
import com.example.todoapp.presentation.screens.list.components.TaskItem
import com.example.todoapp.util.Action
import com.example.todoapp.util.SearchAppBarState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ListContent(
    modifier: Modifier = Modifier,
    allTasks: List<ToDoTask>,
    lowPriorityTasks: List<ToDoTask>,
    highPriorityTasks: List<ToDoTask>,
    sortState: Priority,
    searchedTasks: List<ToDoTask>,
    searchAppBarState: SearchAppBarState,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    when {
        searchAppBarState == SearchAppBarState.TRIGGERED -> {
            HandleListContent(
                modifier = modifier,
                tasks = searchedTasks,
                onSwipeToDelete = onSwipeToDelete,
                navigateToTaskScreen = navigateToTaskScreen
            )
        }

        sortState == Priority.NONE -> {
            HandleListContent(
                modifier = modifier,
                tasks = allTasks,
                onSwipeToDelete = onSwipeToDelete,
                navigateToTaskScreen = navigateToTaskScreen
            )
        }

        sortState == Priority.LOW -> {
            HandleListContent(
                modifier = modifier,
                tasks = lowPriorityTasks,
                onSwipeToDelete = onSwipeToDelete,
                navigateToTaskScreen = navigateToTaskScreen
            )
        }

        sortState == Priority.HIGH -> {
            HandleListContent(
                modifier = modifier,
                tasks = highPriorityTasks,
                onSwipeToDelete = onSwipeToDelete,
                navigateToTaskScreen = navigateToTaskScreen
            )
        }
    }
}

@Composable
private fun HandleListContent(
    modifier: Modifier = Modifier,
    tasks: List<ToDoTask>,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    if (tasks.isEmpty()) {
        EmptyContent(
            modifier = modifier
        )
    } else {
        LazyColumn(
            modifier = modifier
        ) {
            items(
                items = tasks,
                key = { task ->
                    task.id
                }
            ) { task ->
                val scope = rememberCoroutineScope()
                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = {
                        when (it) {
                            SwipeToDismissBoxValue.EndToStart -> {
                                scope.launch {
                                    delay(300)
                                    onSwipeToDelete(Action.DELETE, task)
                                }
                                return@rememberSwipeToDismissBoxState true
                            }

                            else -> return@rememberSwipeToDismissBoxState false
                        }
                    },
                    positionalThreshold = { it * .30f }
                )

                val degrees by animateFloatAsState(
                    if (dismissState.targetValue == SwipeToDismissBoxValue.Settled) {
                        0F
                    } else {
                        -45F
                    }
                )

                var itemAppeared by remember { mutableStateOf(false) }
                LaunchedEffect(key1 = true) {
                    itemAppeared = true
                }

                AnimatedVisibility(
                    visible = itemAppeared && dismissState.targetValue != SwipeToDismissBoxValue.EndToStart,
                    enter = expandVertically(
                        animationSpec = tween(
                            durationMillis = 300
                        )
                    ),
                    exit = shrinkVertically(
                        animationSpec = tween(
                            durationMillis = 300
                        )
                    )
                ) {
                    SwipeToDismissBox(
                        state = dismissState,
                        backgroundContent = { RedBackground(degrees) }
                    ) {
                        TaskItem(
                            toDoTask = task,
                            navigationToTaskScreen = navigateToTaskScreen
                        )
                    }
                }
            }
        }
    }
}