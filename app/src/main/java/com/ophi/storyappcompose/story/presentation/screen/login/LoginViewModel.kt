package com.ophi.storyappcompose.story.presentation.screen.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.ophi.storyappcompose.story.data.repository.AuthRepository
import com.ophi.storyappcompose.story.domain.model.LoginResponse
import com.ophi.storyappcompose.story.presentation.util.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState : MutableStateFlow<AuthState<LoginResponse>> =
        MutableStateFlow(AuthState.Unauthorized)

//    val uiState = _uiState.asStateFlow()

    val uiState: MutableStateFlow<AuthState<LoginResponse>>
        get() = _uiState

    fun login(email: String, password: String) {
        _uiState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                repository.login(email, password)
                    .catch {
                        _uiState.value = AuthState.Error(it.message.toString())
                    }
                    .collect {
                        if (!it.error) {
                            _uiState.value = AuthState.Success(it)
                            Log.d("Login Success", it.loginResult.token)
                        } else {
                            _uiState.value = AuthState.Error("Login Failed")
                            Log.d("Login Failed", "Gagal Login")
                        }
                    }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
                _uiState.value = AuthState.Error(errorResponse.message)
            }
        }
    }
}