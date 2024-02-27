package com.ophi.storyappcompose.story.presentation.screen.story

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.ophi.storyappcompose.story.domain.model.StoryResponse
import com.ophi.storyappcompose.story.domain.repository.AuthRepository
import com.ophi.storyappcompose.story.presentation.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _storyState: MutableStateFlow<UiState<StoryResponse>> =
        MutableStateFlow(UiState.Loading)

    val storyState: StateFlow<UiState<StoryResponse>>
        get() = _storyState


    fun getStories() {
        _storyState.value = UiState.Loading
        try {
            viewModelScope.launch {
                repository.getStories()
                    .catch {
                        _storyState.value = UiState.Error(it.message.toString())
                    }
                    .collect { story ->
                        Log.d("StoryViewModel :", "Mengambil data Story ${story.listStory}")
                        _storyState.value = UiState.Success(story)
                    }
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, StoryResponse::class.java)
            _storyState.value = UiState.Error(errorResponse.message)
        }
    }
}