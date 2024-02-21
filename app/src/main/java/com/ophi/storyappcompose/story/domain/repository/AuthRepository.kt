package com.ophi.storyappcompose.story.domain.repository

import com.ophi.storyappcompose.story.domain.model.LoginResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(email: String, password: String): Flow<LoginResponse>
}