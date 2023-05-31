package com.example.movieapp_compose.model.bottomnavigation

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.movieapp_compose.model.datamodel.MovieDetails
import com.example.movieapp_compose.model.room.database.MovieDatabase
import com.example.movieapp_compose.model.room.repository.MoviesRepository
import com.example.movieapp_compose.ui.favoritesview.FavoriteScreen
import com.example.movieapp_compose.ui.loginview.LoginScreen
import com.example.movieapp_compose.ui.loginview.viewmodel.LoginViewModel
import com.example.movieapp_compose.ui.registerview.RegisterScreen
import com.example.movieapp_compose.ui.registerview.viewmodel.RegisterViewModel
import com.example.movieapp_compose.ui.searchview.SearchScreen
import com.example.movieapp_compose.ui.searchview.viewmodel.MovieViewModel

@Composable
fun BottomNavGraph(navController: NavHostController,movieViewModel: MovieViewModel) {
//    val context = LocalContext.current
//
//    val dataBase = Room.databaseBuilder(
//        context,
//        MovieDatabase::class.java, MovieDatabase.DATABASE_NAME).build()
//
//    val moviesRepository = MoviesRepository(dataBase.movieDao())
//
//    val movieViewModel = remember {
//        MovieViewModel(moviesRepository)
//    }

    NavHost(
        navController = navController,
        startDestination = "SearchScreen"
    ) {
        composable("SearchScreen") {
            SearchScreen(model = movieViewModel)
        }
        composable("FavoriteScreen") {
            FavoriteScreen(viewModel = movieViewModel)
        }
    }
}

@Composable
fun UserNavigation(navController: NavHostController, ) {

    val context = LocalContext.current

    val dataBase = Room.databaseBuilder(
        context,
        MovieDatabase::class.java, MovieDatabase.DATABASE_NAME).build()

    val moviesRepository = MoviesRepository(dataBase.movieDao())

    val movieViewModel = remember {
        MovieViewModel(moviesRepository)
    }

    val loginViewModel = remember {
        LoginViewModel(context)
    }

    val registerViewModel = remember {
        RegisterViewModel(context)
    }
    val sharedPreferences = context.getSharedPreferences("owner", Context.MODE_PRIVATE)
    val id = sharedPreferences.getInt("userId", 0)
    var startDestination = "login"
    if (id != 0) {
        startDestination = "mainscreen"
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable("SignUp") {
            RegisterScreen(controller = navController, viewModel = registerViewModel)
        }
        composable("login") {
            LoginScreen(viewModel = loginViewModel, navController = navController)
        }
        composable("mainscreen") {
            MainScreen(viewModel = movieViewModel, navController)
        }
    }
}

