package com.ophi.storyappcompose.di

import android.content.Context
import android.util.Log
import com.ophi.storyappcompose.story.data.remote.ApiConfig
import com.ophi.storyappcompose.story.data.repository.AuthRepositoryImpl
import com.ophi.storyappcompose.story.domain.pref.UserPreference
import com.ophi.storyappcompose.util.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideUserPreference(@ApplicationContext context: Context): UserPreference {
        try {
            return UserPreference(context.dataStore)
        } catch (e: Exception) {
            Log.e("AppModule", "Error providing UserPreference ${e.message}", e)
            throw e
        }
    }

    @Provides
    fun provideApiConfig(): ApiConfig {
        try {
            return ApiConfig()
        } catch (e: Exception) {
            Log.e("AppModule", "Error providing ApiConfig: ${e.message}", e)
            throw e
        }
    }

    @Provides
    fun provideAuthRepository(@ApplicationContext context: Context): AuthRepositoryImpl {
        val userPreference = provideUserPreference(context)
        val apiService = provideApiConfig().getApiService(context)

        try {
            return AuthRepositoryImpl(
                userPreference,
                apiService
            )
        } catch (e: Exception) {
            Log.e("AppModule", "Error providing AuthRepository: ${e.message}", e)
            throw e
        }
    }
}