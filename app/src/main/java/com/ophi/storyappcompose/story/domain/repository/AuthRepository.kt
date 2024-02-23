package com.ophi.storyappcompose.story.domain.repository

import androidx.lifecycle.LiveData
import com.ophi.storyappcompose.story.domain.model.LoginResponse
import com.ophi.storyappcompose.story.domain.model.StoryResponse
import com.ophi.storyappcompose.story.domain.pref.UserModel
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(email: String, password: String): Flow<LoginResponse>

    suspend fun saveSession(user: UserModel)

    fun getSession(): Flow<UserModel>

    suspend fun logout()

//    suspend fun getStories(): Flow<StoryResponse>
}