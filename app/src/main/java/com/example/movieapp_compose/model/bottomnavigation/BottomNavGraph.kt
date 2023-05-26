package com.example.movieapp_compose.model.bottomnavigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movieapp_compose.ui.favoritesview.FavoriteScreen
import com.example.movieapp_compose.ui.searchview.SearchScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreens.Search.route
    ) {
        composable(route = BottomBarScreens.Search.route) {
            SearchScreen()
        }
        composable(route = BottomBarScreens.Favorite.route) {
            FavoriteScreen()
        }
//        composable("PhotoMapScreen?path={path}") { backStackEntry ->
//            println(backStackEntry)
//            PhotoMapScreen(backStackEntry.arguments?.getString("path") ?: "")
//        }
    }
}