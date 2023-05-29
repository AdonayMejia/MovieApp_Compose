package com.example.movieapp_compose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.movieapp_compose.model.bottomnavigation.MainScreen
import com.example.movieapp_compose.model.datamodel.MovieDetails
import com.example.movieapp_compose.ui.theme.MovieApp_ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieApp_ComposeTheme {
                MainScreen()
            }
        }
    }
}
