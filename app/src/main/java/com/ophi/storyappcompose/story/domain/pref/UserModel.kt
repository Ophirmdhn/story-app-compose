package com.ophi.storyappcompose.story.domain.pref

data class UserModel(
    val name: String,
    val token: String,
    val isLogin: Boolean = false
)
