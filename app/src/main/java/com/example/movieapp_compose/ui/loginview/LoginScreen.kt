package com.example.movieapp_compose.ui.loginview

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieapp_compose.model.bottomnavigation.MainScreen
import com.example.movieapp_compose.ui.searchview.SearchScreen

@Composable
fun LoginScreen(userPreferences: UserPreferences) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login")
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // Perform login validation
            if (username == "admin" && password == "password") {
                userPreferences.userLoggedIn(true)
            } else {
                Toast.makeText(context, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = "Login")
        }
    }
}

@Composable
fun App(userPreferences: UserPreferences) {
    if (userPreferences.isLoggedIn) {
        MainScreen()
    } else {
        // User is not logged in, show the login screen
        LoginScreen(userPreferences)
    }
}

@Composable
fun MyApp(userPreferences: UserPreferences) {
    App(userPreferences)
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoginApp() {
    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context) }
    MyApp(userPreferences)
}