package com.ophi.storyappcompose.story.presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ophi.storyappcompose.story.domain.pref.UserModel
import com.ophi.storyappcompose.story.domain.repository.AuthRepository
import com.ophi.storyappcompose.story.presentation.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<UserModel>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<UserModel>>
        get() = _uiState

    fun getUser() {
        viewModelScope.launch{
            repository.getSession()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}