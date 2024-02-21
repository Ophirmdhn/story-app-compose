package com.ophi.storyappcompose.story.data.repository

import com.ophi.storyappcompose.story.data.remote.ApiService
import com.ophi.storyappcompose.story.domain.model.LoginResponse
import com.ophi.storyappcompose.story.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AuthRepository{
    override suspend fun login(email: String, password: String): Flow<LoginResponse> {
        return flowOf(apiService.login(email, password))
    }
}