package com.ophi.storyappcompose.story.presentation.navigation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ophi.storyappcompose.story.presentation.navigation.NavigationItem
import com.ophi.storyappcompose.story.presentation.navigation.Screen

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = "Home",
                icon = Icons.Outlined.Home,
                iconActive = Icons.Filled.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = "Add Stories",
                icon = Icons.Outlined.AddCircleOutline,
                iconActive = Icons.Filled.AddCircle,
                screen = Screen.AddStory
            ),
            NavigationItem(
                title = "Maps",
                icon = Icons.Outlined.Map,
                iconActive = Icons.Filled.Map,
                screen = Screen.Maps
            ),
        )
        navigationItems.map { item ->
            val isSelected = currentRoute == item.screen.route

            NavigationBarItem(
                selected = false,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                label = {
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = item.title,
                        color = if (isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.secondary
                        }
                    )
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.iconActive else item.icon,
                        contentDescription = item.title,
                        tint = if (isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            Color.Gray
                        }
                    )
                }
            )
        }
    }
}