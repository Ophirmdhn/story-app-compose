package com.ophi.storyappcompose.story.data.repository

import com.ophi.storyappcompose.story.data.remote.ApiService
import com.ophi.storyappcompose.story.domain.model.LoginResponse
import com.ophi.storyappcompose.story.domain.pref.UserModel
import com.ophi.storyappcompose.story.domain.pref.UserPreference
import com.ophi.storyappcompose.story.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) : AuthRepository {
    override suspend fun login(email: String, password: String): Flow<LoginResponse> {
        return flowOf(apiService.login(email, password))
    }

    override suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    override suspend fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    override suspend fun logout() {
        userPreference.logout()
    }
}