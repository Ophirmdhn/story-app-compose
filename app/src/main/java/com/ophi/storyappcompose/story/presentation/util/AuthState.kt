package com.ophi.storyappcompose.story.presentation.util

sealed class AuthState<out T> private constructor(){

    object Unauthorized : AuthState<Nothing>()
    object Loading : AuthState<Nothing>()
    data class Success<out T: Any>(val data: T) : AuthState<T>()
    data class Error(val errorMessage: String) : AuthState<Nothing>()
}