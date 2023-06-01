package com.example.movieapp_compose.ui.registerview.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.movieapp_compose.model.datamodel.UserModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@SuppressLint("StaticFieldLeak")
class RegisterViewModel(private var context: Context) : ViewModel() {

    fun registerUser(username: String, password: String): Boolean {
        val gson = Gson()
        try {
            val listType = object : TypeToken<ArrayList<UserModel>>() {}.type
            val sharedPreferences = context.getSharedPreferences("owner", Context.MODE_PRIVATE)
            val usersJson = sharedPreferences?.getString("users", "")
            if (usersJson == "") {
                val users = ArrayList<UserModel>()
                users.add(
                    UserModel(
                        1,
                        username,
                        password,
                    ),
                )
                val json = gson.toJson(users)
                sharedPreferences.edit().putString("users", json).apply()
            } else {
                val users: ArrayList<UserModel> = gson.fromJson(usersJson, listType)
                users.add(
                    UserModel(
                        users.size + 1,
                        username,
                        password,
                    )
                )
                val json = gson.toJson(users)
                sharedPreferences.edit().putString("users", json).apply()
            }
        } catch (e: Exception) {
            return false
        }
        return true
    }
}