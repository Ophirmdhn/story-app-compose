package com.ophi.storyappcompose.story.data.remote

import android.content.Context
import com.ophi.storyappcompose.BuildConfig
import com.ophi.storyappcompose.story.domain.pref.UserPreference
import com.ophi.storyappcompose.util.Constant.BASE_URL
import com.ophi.storyappcompose.util.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiConfig @Inject constructor() {

    fun getApiService(context: Context): ApiService {

        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val token = getTokenFromContext(context)

            val requestHeaders = req.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(requestHeaders)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }

    private fun getTokenFromContext(context: Context): String {
        val pref = UserPreference(context.dataStore)
        val user = runBlocking {
            pref.getSession().first()
        }
        return user.token
    }
}