package com.example.todoapp.presentation.screens.list.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.todoapp.R
import com.example.todoapp.data.model.Priority
import com.example.todoapp.ui.theme.LARGE_PADDING
import com.example.todoapp.ui.theme.TopAppBarBackgroundColor
import com.example.todoapp.ui.theme.TopAppBarContentColor
import com.example.todoapp.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultListAppBar(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteClicked: () -> Unit
) {
    var expandedSort by remember { mutableStateOf(false) }
    var expandedDelete by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.top_app_bar_title_task),
                color = TopAppBarContentColor
            )
        },
        actions = {
            IconButton(
                onClick = { onSearchClicked() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(R.string.search_task_action),
                    tint = TopAppBarContentColor
                )
            }
            IconButton(
                onClick = { expandedSort = true }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_filter_list),
                    contentDescription = stringResource(R.string.sort_task_action),
                    tint = TopAppBarContentColor
                )

                DropdownMenu(
                    expanded = expandedSort,
                    onDismissRequest = { expandedSort = false }
                ) {

                    DropdownMenuItem(
                        text = { PriorityItem(Priority.LOW) },
                        onClick = {
                            expandedSort = false
                            onSortClicked(Priority.LOW)
                        },
                    )

                    DropdownMenuItem(
                        text = { PriorityItem(Priority.HIGH) },
                        onClick = {
                            expandedSort = false
                            onSortClicked(Priority.HIGH)
                        }
                    )

                    DropdownMenuItem(
                        text = { PriorityItem(Priority.NONE) },
                        onClick = {
                            expandedSort = false
                            onSortClicked(Priority.NONE)
                        }
                    )
                }
            }

            IconButton(
                onClick = { expandedDelete = true }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_vertical_menu),
                    contentDescription = stringResource(R.string.delete_all_action),
                    tint = TopAppBarContentColor
                )
                DropdownMenu(
                    expanded = expandedDelete,
                    onDismissRequest = { expandedDelete = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                modifier = Modifier.padding(start = LARGE_PADDING),
                                text = stringResource(R.string.delete_all_action),
                                style = Typography.bodyLarge
                            )
                        },
                        onClick = {
                            expandedDelete = false
                            onDeleteClicked()
                        }
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = TopAppBarBackgroundColor
        )
    )
}