package com.ophi.storyappcompose.story.data.repository

import com.ophi.storyappcompose.story.data.remote.ApiService
import com.ophi.storyappcompose.story.domain.model.LoginResponse
import com.ophi.storyappcompose.story.domain.model.StoryResponse
import com.ophi.storyappcompose.story.domain.pref.UserModel
import com.ophi.storyappcompose.story.domain.pref.UserPreference
import com.ophi.storyappcompose.story.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) : AuthRepository {
    override suspend fun login(email: String, password: String): Flow<LoginResponse> {
        return flowOf(apiService.login(email, password)).onEach { loginResponse ->
            if (!loginResponse.error) {
                saveSession(
                    UserModel(
                        name = loginResponse.loginResult.name,
                        token = loginResponse.loginResult.token,
                        isLogin = true
                    )
                )
            }
        }
    }

    override suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    override fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    override suspend fun logout() {
        userPreference.logout()
    }

    override suspend fun getStories(): Flow<StoryResponse> {
        return flowOf(apiService.getStories())
    }

}