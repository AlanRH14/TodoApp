package com.example.todoapp.presentation.screens.task.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.data.model.Priority
import com.example.todoapp.presentation.screens.list.components.PriorityItem
import com.example.todoapp.ui.theme.BORDER_RADIUS_DROPDOWN
import com.example.todoapp.ui.theme.PRIORITY_DROP_DOWN_HEIGHT
import com.example.todoapp.ui.theme.PRIORITY_INDICATOR_SIZE
import com.example.todoapp.ui.theme.Typography

@Composable
fun PriorityDropDown(
    modifier: Modifier = Modifier,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    var expended by remember { mutableStateOf(false) }
    val angle: Float by animateFloatAsState(
        targetValue = if (expended) 180F else 0F,
        label = stringResource(R.string.angle_row)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(PRIORITY_DROP_DOWN_HEIGHT)
            .clickable { expended = true }
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.58f),
                shape = RoundedCornerShape(BORDER_RADIUS_DROPDOWN),
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = Modifier
                .size(PRIORITY_INDICATOR_SIZE)
                .weight(weight = 1F),
        ) {
            drawCircle(
                color = priority.color
            )
        }

        Text(
            modifier = Modifier
                .weight(weight = 8F),
            text = priority.name,
            style = Typography.bodyLarge,
        )
        IconButton(
            modifier = Modifier
                .alpha(alpha = 0.6F)
                .rotate(degrees = angle)
                .weight(weight = 1.5F),
            onClick = { expended = true }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = stringResource(R.string.drop_down_arrow_icon),
            )
        }

        DropdownMenu(
            modifier = Modifier.fillMaxWidth(fraction = 0.94F),
            expanded = expended,
            onDismissRequest = {
                expended = false
            }
        ) {
            Priority.entries.forEach { priority ->
                if (priority != Priority.NONE) {
                    DropdownMenuItem(
                        onClick = {
                            expended = false
                            onPrioritySelected(priority)
                        },
                        text = {
                            PriorityItem(priority = priority)
                        }
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun PriorityDropDownPreview() {
    PriorityDropDown(
        priority = Priority.LOW,
        onPrioritySelected = {}
    )
}