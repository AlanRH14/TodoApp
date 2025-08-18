package com.example.todoapp.presentation.screens.task.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.todoapp.R
import com.example.todoapp.ui.theme.TopAppBarBackgroundColor
import com.example.todoapp.ui.theme.TopAppBarContentColor
import com.example.todoapp.util.Action

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskAppBar(
    navigateToListScreen: (Action) -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                stringResource(R.string.add_task_title),
                color = TopAppBarContentColor,
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { navigateToListScreen(Action.NO_ACTION) }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back_arrow_button),
                    tint = TopAppBarContentColor
                )
            }
        },
        actions = {
            IconButton(
                onClick = { navigateToListScreen(Action.ADD) }
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = stringResource(R.string.add_task_title),
                    tint = TopAppBarContentColor
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = TopAppBarBackgroundColor
        ),
    )
}

@Composable
@Preview
fun NewTaskAppBarPreview() {
    NewTaskAppBar(
        navigateToListScreen = {}
    )
}