package com.ophi.storyappcompose.story.presentation.screen.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ophi.storyappcompose.story.presentation.util.AuthState
import com.ophi.storyappcompose.story.presentation.util.component.Loading

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {

    var username by rememberSaveable (stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var password by rememberSaveable (stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    val context = LocalContext.current
    LoginContent(
        usernameText = username,
        passwordText = password,
        onUsernameChange = { username = it },
        onPasswordChange = { password = it },
        onLoginClick = { username, password ->
            viewModel.login(username, password)
        },
        modifier = modifier
    )

    val uiState = viewModel.uiState.collectAsState(AuthState.Unauthorized).value
    val isLoading = remember { mutableStateOf(false) }

    if (isLoading.value) { Loading() }

    LaunchedEffect(key1 = uiState) {
        when (uiState) {
            is AuthState.Loading -> {
                isLoading.value = true
            }

            is AuthState.Success -> {
                Toast.makeText(context, "Login Berhasil", Toast.LENGTH_SHORT).show()
                Log.d("Login", "Login Berhasil")
                isLoading.value = false
            }

            is AuthState.Error -> {
                Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
                isLoading.value = false
            }

            else -> {}
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    usernameText: TextFieldValue = TextFieldValue(""),
    passwordText: TextFieldValue = TextFieldValue(""),
    onUsernameChange: (TextFieldValue) -> Unit = {},
    onPasswordChange: (TextFieldValue) -> Unit = {},
    onLoginClick: (username: String, password: String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 32.dp, end = 32.dp, bottom = 64.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Bottom,
    ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = "Selamat Datang!",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp
                )
            )
            Text(
                text = "Login sekarang untuk melanjutkan",
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp
                )
            )
        }
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "Email",
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp
            )
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = usernameText,
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null
                )
            },
            onValueChange = onUsernameChange,
            label = {
                Text(
                    text = "Masukan email anda",
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp
                    )
                )
            }
        )
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "Password",
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp
            )
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = passwordText,
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null
                )
            },
//            trailingIcon = {
//                IconButton(onClick =  onVisiblePasswordChange ) {
//                    val visibilityIcon =
//                        if (visiblePassword) Icons.Filled.Vis else Icons.Filled.VisibilityOff
//                    // Please provide localized description for accessibility services
//                    val description = if (visiblePassword) "Show password" else "Hide password"
//                    Icon(imageVector = visibilityIcon, contentDescription = description)
//                }
//            },
            onValueChange = onPasswordChange,
            maxLines = 1,
            label = {
                Text(
                    text = "Masukan password anda",
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp
                    )
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            onClick = {
                onLoginClick(usernameText.text, passwordText.text)
            }
        ) {
            Text(
                text = "Login",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp
                )
            )
        }
    }
}