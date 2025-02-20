package com.example.todoapp.presentation.list.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.todoapp.R
import com.example.todoapp.ui.theme.ICON_EMPTY_SCREEN
import com.example.todoapp.ui.theme.MediumGray
import com.example.todoapp.ui.theme.Typography

@Composable
fun EmptyContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(ICON_EMPTY_SCREEN),
            painter = painterResource(id = R.drawable.ic_sad_face),
            contentDescription = stringResource(R.string.sad_face_icon),
            tint = MediumGray
        )

        Text(
            text = stringResource(R.string.empty_task_content),
            color = MediumGray,
            fontWeight = FontWeight.Bold,
            fontSize = Typography.headlineSmall.fontSize
        )
    }
}

@Composable
@Preview
fun EmptyContentPreview() {
    EmptyContent()
}