package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.navigation.NavGraph
import com.example.todoapp.presentation.viewmodel.SharedViewModel
import com.example.todoapp.ui.theme.TodoAppTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            TodoAppTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}