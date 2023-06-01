package com.example.movieapp_compose.model.bottomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Movie
import androidx.compose.material.icons.twotone.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreens(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Search : BottomBarScreens(
        "SearchScreen",
        title = "Search Movies",
        icon = Icons.TwoTone.Movie
    )

    object Favorite : BottomBarScreens(
        "FavoriteScreen",
        title = "Favorites Movies",
        icon = Icons.TwoTone.Star
    )
}