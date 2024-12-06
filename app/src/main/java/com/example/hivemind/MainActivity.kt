package com.example.hivemind

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.hivemind.navigation.NavigationHost
import com.example.hivemind.ui.theme.HiveMindTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Access SharedPreferences to check login status and user details
        val sharedPreferences = getSharedPreferences("UserLoginPrefs", Context.MODE_PRIVATE)
        val isTutor = sharedPreferences.getBoolean("isTutor", false)
        val isLoggedIn = sharedPreferences.contains("isTutor")
        val currentUserId = sharedPreferences.getInt("userId", -1) // Get the logged-in user's ID

        if (!isLoggedIn || currentUserId == -1) {
            // Redirect to LoginActivity if the user is not logged in or ID is invalid
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        // If logged in, proceed to the NavigationHost
        setContent {
            HiveMindTheme {
                val navController = rememberNavController()
                NavigationHost(
                    navController = navController,
                    isTutor = isTutor,
                    currentUserId = currentUserId // Pass the user ID to NavigationHost
                )
            }
        }
    }
}
