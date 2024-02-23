package com.ophi.storyappcompose.story.presentation.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    object Story : Screen("story")
    object AddStory : Screen("add_story")
    object Maps : Screen("maps")
    object Splash : Screen("splash")
}
