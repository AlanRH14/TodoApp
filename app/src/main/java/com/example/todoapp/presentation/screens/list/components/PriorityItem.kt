package com.example.todoapp.presentation.screens.list.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.todoapp.data.model.Priority
import com.example.todoapp.ui.theme.LARGE_PADDING
import com.example.todoapp.ui.theme.PRIORITY_INDICATOR_SIZE
import com.example.todoapp.ui.theme.TextUiSystemColor
import com.example.todoapp.ui.theme.Typography

@Composable
fun PriorityItem(
    priority: Priority
) {
    Row {
        Canvas(
            modifier = Modifier.size(PRIORITY_INDICATOR_SIZE),
        ) {
            drawCircle(color = priority.color)
        }

        Text(
            modifier = Modifier.padding(start = LARGE_PADDING),
            text = priority.name,
            color = TextUiSystemColor,
            style = Typography.bodyLarge
        )
    }
}

@Composable
@Preview
fun PriorityItemPreview() {
    PriorityItem(
        Priority.HIGH
    )
}