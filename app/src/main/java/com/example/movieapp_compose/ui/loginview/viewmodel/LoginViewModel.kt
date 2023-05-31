package com.example.movieapp_compose.ui.loginview.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.movieapp_compose.model.datamodel.UserModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("StaticFieldLeak")
class LoginViewModel( private val context: Context) : ViewModel(){

    fun login(username: String, password: String) : String? {
        if (username.isBlank() || password.isBlank()) {
            return "Username or Password is empty"
        }
        val sharedPreferences = context.getSharedPreferences("owner", Context.MODE_PRIVATE)
        val usersJson = sharedPreferences?.getString("users", "")
        if (usersJson.isNullOrBlank()) {
            return "There are not User Register"
        }
        val listType = object : TypeToken<ArrayList<UserModel>>() {}.type
        val gson = Gson()
        val users : ArrayList<UserModel> = gson.fromJson(usersJson, listType)
        users.forEach {user ->
            if (user.username == username && user.password == password) {
                onLoginSuccess(user)
                return null
            }
        }
        return "Username or Password are incorrect"
    }

    @SuppressLint("SimpleDateFormat")
    private fun onLoginSuccess(user: UserModel) {
        val sharedPreferences = context.getSharedPreferences("owner", Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt("userId", user.id).apply()
        sharedPreferences.edit().putString("userName", user.username).apply()
        sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())
        sharedPreferences.edit().putString("connection", currentDate).apply()
    }
}