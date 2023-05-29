package com.example.movieapp_compose.ui.loginview

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


class UserPreferences(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences("login_preferences", Context.MODE_PRIVATE)

    var isLoggedIn by mutableStateOf(sharedPreferences.getBoolean("isLoggedIn", false))
        private set

    fun userLoggedIn(value: Boolean){
        isLoggedIn = value
        sharedPreferences.edit().putBoolean("isLoggedIn", value).apply()
    }
}