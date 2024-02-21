package com.ophi.storyappcompose.story.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ophi.storyappcompose.story.presentation.navigation.component.BottomBar
import com.ophi.storyappcompose.story.presentation.screen.home.HomeScreen
import com.ophi.storyappcompose.story.presentation.screen.login.LoginScreen
import com.ophi.storyappcompose.story.presentation.screen.maps.MapsScreen
import com.ophi.storyappcompose.story.presentation.screen.story.StoryScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute !in listOf(
                Screen.Login.route
            )) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Login.route) {
                LoginScreen(navController)
            }
            composable(Screen.Home.route) {
                HomeScreen()
            }
            composable(Screen.Story.route) {
                StoryScreen()
            }
            composable(Screen.Maps.route) {
                MapsScreen()
            }
        }
    }
}