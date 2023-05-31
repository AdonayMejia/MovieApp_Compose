package com.example.movieapp_compose.model.bottomnavigation

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.movieapp_compose.R
import com.example.movieapp_compose.model.datamodel.MovieDetails
import com.example.movieapp_compose.ui.searchview.viewmodel.MovieViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MovieViewModel, navRoot: NavHostController) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("owner", Context.MODE_PRIVATE)
    val lastConnection = sharedPreferences.getString("connection", "")
    val user = sharedPreferences.getString("userName","")
    Column(modifier = Modifier.fillMaxSize()) {
        ModalDrawer(
            drawerState = drawerState,
            gesturesEnabled = drawerState.isOpen,
            drawerContent = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        model = R.drawable.cuevana,
                        contentDescription = "Image",
                        modifier = Modifier
                            .size(90.dp)
                            .fillMaxSize(),
                        alignment = Alignment.Center)
                    Spacer(modifier = Modifier.padding(16.dp))
                    Text(text = "User Creation: $lastConnection")
                    Spacer(modifier = Modifier.padding(16.dp))
                    Text(text = "User: $user")
                    Spacer(modifier = Modifier.padding(16.dp))
                    Button(onClick = {
                        viewModel.deleteUserId(context)
                        navRoot.apply {
                            val route = "login"
                            navigate(route = route) {
                                popUpTo(graph.startDestinationId) { inclusive = true }
                            }
                            graph.setStartDestination(route)
                        }
                    }) {
                        Text(text = "Logout")
                    }
                }
            },
            content = {
                Column {
                    TopAppBar(
                        title = { Text(text = "Cuevana", fontSize = 20.sp) },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        if (drawerState.isOpen) {
                                            drawerState.close()
                                        } else {
                                            drawerState.open()
                                        }
                                    }

                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu"
                                )
                            }
                        }
                    )
                    Scaffold(
                        bottomBar = {
                            BottomBar(navController = navController)
                        }
                    ) {
                        BottomNavGraph(navController = navController, viewModel)
                    }
                }

            }
        )

    }

}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreens.Search,
        BottomBarScreens.Favorite
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        screens.forEach { screen ->
            Item(
                screens = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.Item(
    screens: BottomBarScreens,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        label = {
            Text(text = screens.title)
        },
        icon = {
            Icon(imageVector = screens.icon, contentDescription = "Navigation")
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screens.route
        } == true,
        onClick = {
            navController.navigate(screens.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}