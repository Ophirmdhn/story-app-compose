package com.ophi.storyappcompose.story.data.repository

import com.ophi.storyappcompose.story.data.remote.ApiService
import com.ophi.storyappcompose.story.domain.model.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun login(email: String, password: String): Flow<LoginResponse> {
        return flowOf(apiService.login(email, password))
    }
}