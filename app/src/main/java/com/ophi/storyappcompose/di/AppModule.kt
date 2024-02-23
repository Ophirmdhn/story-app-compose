package com.ophi.storyappcompose.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.ophi.storyappcompose.story.data.remote.ApiConfig
import com.ophi.storyappcompose.story.data.remote.ApiService
import com.ophi.storyappcompose.story.domain.pref.UserPreference
import com.ophi.storyappcompose.util.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideApi(dataStore: UserPreference): ApiService {
        val user = runBlocking {
            dataStore.getSession().first()
        }
        return ApiConfig.getApiService(user.token)
    }

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }
}