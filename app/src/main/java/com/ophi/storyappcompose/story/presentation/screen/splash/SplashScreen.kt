package com.ophi.storyappcompose.story.presentation.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ophi.storyappcompose.R
import com.ophi.storyappcompose.story.presentation.navigation.Screen
import com.ophi.storyappcompose.story.presentation.util.UiState
import com.ophi.storyappcompose.ui.theme.StoryAppComposeTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: SplashViewModel = hiltViewModel()
) {

    val state = viewModel.uiState.collectAsState(UiState.Loading).value

    LaunchedEffect(key1 = state) {
        delay(1000L)
        when (state) {
            is UiState.Loading -> {
                viewModel.getUser()
            }

            is UiState.Success -> {
                if (state.data.isLogin) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true}
                    }
                } else {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true}
                    }
                }
            }

            is UiState.Error -> {
                navController.navigate(Screen.Login.route)
            }
        }


    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_splash),
            contentDescription = "Logo Splash",
            modifier = Modifier
                .size(250.dp)
                .padding(start = 15.dp)
        )
    }
}
