package com.ophi.storyappcompose.story.presentation.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ophi.storyappcompose.story.domain.pref.UserModel
import com.ophi.storyappcompose.story.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            _isLoading.value = true
            delay(1000)
            getUser()
            _isLoading.value = false
        }
    }

    fun getUser(): LiveData<UserModel> = repository.getSession().asLiveData()
}