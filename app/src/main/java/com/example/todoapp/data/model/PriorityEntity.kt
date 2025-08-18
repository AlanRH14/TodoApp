package com.example.todoapp.data.model

import androidx.compose.ui.graphics.Color
import com.example.todoapp.ui.theme.HighPriorityColor
import com.example.todoapp.ui.theme.LowPriorityColor
import com.example.todoapp.ui.theme.MediumPriorityColor
import com.example.todoapp.ui.theme.NonePriorityColor

enum class Priority(val color: Color) {
    HIGH(color = HighPriorityColor),
    MEDIUM(color = MediumPriorityColor),
    LOW(color = LowPriorityColor),
    NONE(color = NonePriorityColor)
}