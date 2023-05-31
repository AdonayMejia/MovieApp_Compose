package com.example.movieapp_compose.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.movieapp_compose.model.bottomnavigation.MainScreen
import com.example.movieapp_compose.model.datamodel.MovieDetails
import com.example.movieapp_compose.model.room.database.MovieDatabase
import com.example.movieapp_compose.model.room.repository.MoviesRepository
import com.example.movieapp_compose.ui.loginview.LoginScreen
import com.example.movieapp_compose.ui.loginview.viewmodel.LoginViewModel
import com.example.movieapp_compose.ui.theme.MovieApp_ComposeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            val viewModel = LoginViewModel(context)
            val navController = rememberNavController()
            MovieApp_ComposeTheme {
                LoginScreen(viewModel = viewModel , navController = navController)
            }
        }
    }
}
