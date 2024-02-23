package com.ophi.storyappcompose.story.presentation.screen.story

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ophi.storyappcompose.story.domain.model.StoryResponse
import com.ophi.storyappcompose.story.domain.repository.AuthRepository
import com.ophi.storyappcompose.story.presentation.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _storyState: MutableStateFlow<UiState<StoryResponse>> =
        MutableStateFlow(UiState.Loading)

    val storyState: StateFlow<UiState<StoryResponse>>
        get() = _storyState

//    fun getStories() {
//        viewModelScope.launch {
//            repository.getStories()
//                .catch {
//                    _storyState.value = UiState.Error(it.message.toString())
//                }
//                .collect {
//                    _storyState.value = UiState.Success(it)
//                }
//        }
//    }
}