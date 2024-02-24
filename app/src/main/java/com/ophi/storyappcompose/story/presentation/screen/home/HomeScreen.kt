package com.ophi.storyappcompose.story.presentation.screen.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ophi.storyappcompose.story.domain.pref.UserModel
import com.ophi.storyappcompose.story.presentation.navigation.Screen
import com.ophi.storyappcompose.story.presentation.screen.story.StoryScreen
import com.ophi.storyappcompose.story.presentation.util.UiState
import com.ophi.storyappcompose.story.presentation.util.component.Loading
import com.ophi.storyappcompose.ui.theme.StoryAppComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

    viewModel.uiState.collectAsState(UiState.Loading).value.let { state ->
        when (state) {
            is UiState.Success -> {
                Loading()
                if (state.data.isLogin) {
                    Scaffold(
                        topBar = {
                            HomeTopBar(
                                title = "Hello",
                                user = state.data,
                                onLogoutClick = {
                                    navController.navigate(Screen.Login.route) {
                                        viewModel.logout()
                                        popUpTo(Screen.Home.route) { inclusive = true}
                                        popUpTo(Screen.Splash.route) { inclusive = true}
                                    }
                                    Log.d("Home Screen : ", "Menghapus token/sesi")
                                }
                            )
                        },
                        modifier = modifier
                    ) { innerPadding ->
                        Column(
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            StoryScreen()
                        }
                    }
                    Log.d("Home Screen :", "Berhasil mengambil data user : name=${state.data}")
                } else {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true}
                    }
                    Log.d("Home Screen :", "Gagal mengambil data user")
                }
            }

            is UiState.Loading -> {
                viewModel.getUser()
                Loading()
            }

            is UiState.Error -> {
                Screen.Login.route
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    title: String,
    user: UserModel,
    onLogoutClick: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.shadow(2.dp),
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        title = {
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = "$title, ${user.name}",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 18.sp
                )
            )
        },
        actions = {
            IconButton(onClick = onLogoutClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = "Menu Logout"
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    StoryAppComposeTheme {
        HomeScreen(navController = rememberNavController())
    }
}