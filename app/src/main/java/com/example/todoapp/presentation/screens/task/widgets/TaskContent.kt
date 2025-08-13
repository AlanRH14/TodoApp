package com.example.todoapp.presentation.screens.task.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.todoapp.R
import com.example.todoapp.data.model.Priority
import com.example.todoapp.presentation.screens.list.ListUIEvent
import com.example.todoapp.presentation.screens.task.components.PriorityDropDown
import com.example.todoapp.ui.theme.LARGE_PADDING
import com.example.todoapp.ui.theme.MEDIUM_PADDING
import com.example.todoapp.ui.theme.Typography

@Composable
fun TaskContent(
    modifier: Modifier = Modifier,
    title: String,
    onEvent: (ListUIEvent) -> Unit,
    description: String,
    priority: Priority,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = LARGE_PADDING)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MEDIUM_PADDING),
            value = title,
            onValueChange = { onEvent(ListUIEvent.OnTaskTitleUpdate(taskTile = it)) },
            label = { Text(text = stringResource(R.string.add_task_title)) },
            textStyle = Typography.bodyLarge,
            singleLine = true
        )

        HorizontalDivider(
            thickness = MEDIUM_PADDING,
            color = MaterialTheme.colorScheme.background
        )

        PriorityDropDown(
            priority = priority,
            onPrioritySelected = { onEvent(ListUIEvent.OnPriorityUpdate(priority = it)) }
        )

        HorizontalDivider(
            thickness = MEDIUM_PADDING,
            color = MaterialTheme.colorScheme.background
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = description,
            onValueChange = { onEvent(ListUIEvent.OnDescriptionUpdate(description = it)) },
            label = { Text(text = stringResource(R.string.description)) },
            textStyle = Typography.bodyLarge
        )
    }
}

@Composable
@Preview(showBackground = true)
fun TaskContentPreview() {
    TaskContent(
        title = "Lord Miau",
        onEvent = {},
        description = "Some ramdom text",
        priority = Priority.HIGH,
    )
}