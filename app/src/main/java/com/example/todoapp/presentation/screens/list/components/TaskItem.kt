package com.example.todoapp.presentation.screens.list.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.data.local.mock_data.TaskProvider.taskItemTest
import com.example.todoapp.data.local.database.entities.ToDoTask
import com.example.todoapp.presentation.screens.list.widgets.RedBackground
import com.example.todoapp.ui.theme.LARGE_PADDING
import com.example.todoapp.ui.theme.PRIORITY_INDICATOR_SIZE
import com.example.todoapp.ui.theme.TASK_ITEM_ELEVATION
import com.example.todoapp.ui.theme.TaskItemBackgroundColor
import com.example.todoapp.ui.theme.TaskItemTextColor

@Composable
fun TaskItem(
    toDoTask: ToDoTask,
    navigationToTaskScreen: (taskId: Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            contentColor = TaskItemBackgroundColor
        ),
        shape = RectangleShape,
        elevation = CardDefaults.elevatedCardElevation(
            TASK_ITEM_ELEVATION
        ),
        onClick = {
            navigationToTaskScreen(toDoTask.id)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(all = LARGE_PADDING)
                .fillMaxWidth()
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(8F),
                    text = toDoTask.title,
                    color = TaskItemTextColor,
                    style = Typography().headlineSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Box(
                    modifier = Modifier
                        .weight(1F)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(PRIORITY_INDICATOR_SIZE)
                    ) {
                        drawCircle(
                            color = toDoTask.priority.color
                        )
                    }
                }
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = toDoTask.description,
                color = TaskItemTextColor,
                style = Typography().titleSmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
@Preview
private fun TaskItemPreview() {
    TaskItem(
        toDoTask = taskItemTest
    ) { }
}

@Composable
@Preview
private fun RedBackgroundPreview() {
    Column(modifier = Modifier.height(85.dp)) {
        RedBackground( degrees = 0F)
    }
}