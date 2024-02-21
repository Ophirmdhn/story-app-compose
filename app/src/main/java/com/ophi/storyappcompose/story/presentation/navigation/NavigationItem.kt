package com.ophi.storyappcompose.story.presentation.navigation

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val iconActive: ImageVector,
    val screen: Screen
)
