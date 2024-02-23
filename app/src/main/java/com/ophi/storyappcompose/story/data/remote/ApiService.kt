package com.ophi.storyappcompose.story.data.remote

import com.ophi.storyappcompose.story.domain.model.LoginResponse
import com.ophi.storyappcompose.story.domain.model.StoryResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("stories")
    suspend fun getStories(): StoryResponse

}