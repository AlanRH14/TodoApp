package com.example.todoapp.presentation.screens.list.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.example.todoapp.R
import com.example.todoapp.data.model.Priority
import com.example.todoapp.presentation.components.DisplayAlertDialog
import com.example.todoapp.presentation.screens.list.components.DefaultListAppBar
import com.example.todoapp.presentation.screens.list.components.ListSearchAppBar
import com.example.todoapp.util.Action
import com.example.todoapp.util.SearchAppBarState

@Composable
fun ListAppBar(
    searchAppBarState: SearchAppBarState,
    searchTextState: String,
    onSearch: (String) -> Unit,
    onSearchTextChange: (String) -> Unit,
    onSearchActionClicked: (SearchAppBarState) -> Unit,
    onSortClicked: (Priority) -> Unit,
    onBarActionClicked: (Action) -> Unit
) {
    when (searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            var openDialog by remember { mutableStateOf(false) }

            DisplayAlertDialog(
                title = stringResource(R.string.delete_all_tasks),
                message = stringResource(R.string.delete_all_tasks_confirmation),
                openDialog = openDialog,
                closeDialog = { openDialog = false },
                onConfirmClicked = { onBarActionClicked(Action.DELETE_ALL) }
            )

            DefaultListAppBar(
                onSearchClicked = {
                    onSearchActionClicked(SearchAppBarState.OPENED)
                },
                onSortClicked = onSortClicked,
                onDeleteClicked = {
                    openDialog = true
                },
            )
        }

        else -> {
            ListSearchAppBar(
                text = searchTextState,
                onTextChange = { onSearchTextChange(it) },
                onSearchActionClicked = onSearch,
                onCloseClicked = { onSearchActionClicked(SearchAppBarState.CLOSED) }
            )
        }
    }
}