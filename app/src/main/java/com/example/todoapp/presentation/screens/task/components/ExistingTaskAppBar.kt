package com.example.todoapp.presentation.screens.task.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.todoapp.R
import com.example.todoapp.data.mock_data.TaskProvider.taskItemTest
import com.example.todoapp.data.model.ToDoTask
import com.example.todoapp.ui.theme.TopAppBarBackgroundColor
import com.example.todoapp.ui.theme.TopAppBarContentColor
import com.example.todoapp.util.Action

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExistingTaskAppBar(
    task: ToDoTask,
    navigateToListScreen: (Action) -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = task.title,
                color = TopAppBarContentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { navigateToListScreen(Action.NO_ACTION) }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.close_icon),
                    tint = TopAppBarContentColor
                )
            }
        },
        actions = {
            IconButton(
                onClick = { navigateToListScreen(Action.DELETE) }
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(R.string.delete_icon),
                    tint = TopAppBarContentColor
                )
            }

            IconButton(
                onClick = { navigateToListScreen(Action.UPDATE) }
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = stringResource(R.string.update_icon),
                    tint = TopAppBarContentColor
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = TopAppBarBackgroundColor
        )
    )
}

@Composable
@Preview
fun ExistingTaskAppBarPreview() {
    ExistingTaskAppBar(
        task = taskItemTest,
        navigateToListScreen = {}
    )
}