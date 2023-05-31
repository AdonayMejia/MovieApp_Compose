package com.example.movieapp_compose.model.bottomnavigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.room.Room
import com.example.movieapp_compose.model.datamodel.MovieDetails
import com.example.movieapp_compose.model.room.database.MovieDatabase
import com.example.movieapp_compose.model.room.repository.MoviesRepository
import com.example.movieapp_compose.ui.favoritesview.FavoriteScreen
import com.example.movieapp_compose.ui.loginview.LoginScreen
import com.example.movieapp_compose.ui.loginview.viewmodel.LoginViewModel
import com.example.movieapp_compose.ui.searchview.SearchScreen
import com.example.movieapp_compose.ui.searchview.viewmodel.MovieViewModel

@Composable
fun BottomNavGraph(navController: NavHostController) {
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

    NavHost(
        navController = navController,
        startDestination = BottomBarScreens.Search.route
    ) {
        composable(route = BottomBarScreens.Search.route) {
            SearchScreen(model = movieViewModel)
        }
        composable(route = BottomBarScreens.Favorite.route) {
            FavoriteScreen(viewModel = movieViewModel)
        }
        composable("login") {
            LoginScreen(viewModel = loginViewModel, navController = navController)
        }
        composable("mainScreen") {
            MainScreen()
        }
//        composable("PhotoMapScreen?path={path}") { backStackEntry ->
//            println(backStackEntry)
//            PhotoMapScreen(backStackEntry.arguments?.getString("path") ?: "")
//        }
    }
}