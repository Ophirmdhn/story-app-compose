package com.ophi.storyappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ophi.storyappcompose.story.presentation.navigation.NavGraph
import com.ophi.storyappcompose.story.presentation.navigation.NavViewModel
import com.ophi.storyappcompose.ui.theme.StoryAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: NavViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                return@setKeepOnScreenCondition viewModel.isLoading.value
            }
        }
        setContent {
            StoryAppComposeTheme {
                NavGraph()
            }
        }
    }
}