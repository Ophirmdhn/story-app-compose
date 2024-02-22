package com.ophi.storyappcompose.story.domain.repository

import com.ophi.storyappcompose.story.domain.model.LoginResponse
import com.ophi.storyappcompose.story.domain.pref.UserModel
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(email: String, password: String): Flow<LoginResponse>

    suspend fun saveSession(user: UserModel)

    suspend fun getSession(): Flow<UserModel>

    suspend fun logout()
}