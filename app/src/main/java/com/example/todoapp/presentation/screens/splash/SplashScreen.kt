package com.example.todoapp.presentation.screens.splash

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.ui.theme.LOGO_HEIGHT
import com.example.todoapp.ui.theme.SplashColor
import com.example.todoapp.util.Constants.SPLASH_SCREEN_DELAY
import kotlinx.coroutines.delay

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun SplashScreen(
    navigateToListScreen: () -> Unit
) {
    var startAnimation by remember { mutableStateOf(false) }
    val offsetState by animateDpAsState(
        targetValue = if (startAnimation) 0.dp else 100.dp,
        animationSpec = tween(
            durationMillis = 1000
        )
    )
    val alphaState by animateFloatAsState(
        targetValue = if (startAnimation) 1F else 0F,
        animationSpec = tween(
            durationMillis = 1000
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(SPLASH_SCREEN_DELAY)
        navigateToListScreen()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SplashColor),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .size(LOGO_HEIGHT)
                .offset(y = offsetState)
                .alpha(alpha = alphaState),
            painter = painterResource(id = getImage()),
            contentDescription = stringResource(R.string.to_do_logo)
        )
    }
}

@Composable
private fun getImage(): Int =
    if (isSystemInDarkTheme()) {
        R.drawable.ic_logo_dark
    } else {
        R.drawable.ic_logo_light
    }

@Composable
@Preview
private fun SplashScreenPreview() {
    SplashScreen(
        navigateToListScreen = {}
    )
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SplashScreenDarkModePreview() {
    SplashScreen(
        navigateToListScreen = {}
    )
}

