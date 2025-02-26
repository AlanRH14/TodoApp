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
import com.example.todoapp.presentation.screens.task.components.PriorityDropDown
import com.example.todoapp.ui.theme.LARGE_PADDING
import com.example.todoapp.ui.theme.MEDIUM_PADDING
import com.example.todoapp.ui.theme.Typography

@Composable
fun TaskContent(
    modifier: Modifier = Modifier,
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit
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
            onValueChange = { onTitleChange(it) },
            label = { Text(text = stringResource(R.string.add_task_title)) },
            textStyle = Typography.bodySmall,
            singleLine = true
        )

        HorizontalDivider(
            thickness = MEDIUM_PADDING,
            color = MaterialTheme.colorScheme.background
        )

        PriorityDropDown(
            priority = priority, onPrioritySelected = { onPrioritySelected(it) }
        )

        HorizontalDivider(
            thickness = MEDIUM_PADDING,
            color = MaterialTheme.colorScheme.background
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = description,
            onValueChange = { onDescriptionChange(it) },
            label = { Text(text = stringResource(R.string.description)) },
            textStyle = Typography.bodySmall
        )
    }
}

@Composable
@Preview(showBackground = true)
fun TaskContentPreview() {
    TaskContent(
        title = "Lord Miau",
        onTitleChange = {},
        description = "Some ramdom text",
        onDescriptionChange = {},
        priority = Priority.HIGH,
        onPrioritySelected = {}
    )
}