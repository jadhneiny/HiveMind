package com.example.hivemind.ui.theme.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hivemind.models.User
import com.example.hivemind.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun TutorsByCourseScreen(courseName: String, navController: NavController) {
    // Use mutableStateOf with a specific type to resolve `TypeVariable(T)` issues
    var tutors: List<User> by remember { mutableStateOf(emptyList()) }
    var isLoading: Boolean by remember { mutableStateOf(true) }
    var errorMessage: String? by remember { mutableStateOf(null) }

    LaunchedEffect(courseName) {
        isLoading = true
        ApiClient.apiService.getTutorsByCourse(courseName).enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    tutors = response.body() ?: emptyList()
                    errorMessage = if (tutors.isEmpty()) "No tutors available for this course." else null
                } else {
                    errorMessage = "Failed to fetch tutors. Please try again."
                }
                isLoading = false
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e("TutorsByCourseScreen", "Error fetching tutors", t)
                errorMessage = "An error occurred. Please try again."
                isLoading = false
            }
        })
    }

    // UI Layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFAD42F7))
            .padding(16.dp)
    ) {
        Text(
            text = "Tutors for $courseName",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (isLoading) {
            // Show a loading indicator
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        } else if (errorMessage != null) {
            // Show error message
            Text(
                text = errorMessage ?: "Unknown error.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                modifier = Modifier.padding(top = 16.dp)
            )
        } else {
            // Show tutors in a scrollable list
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(tutors) { tutor ->
                    TutorCard(tutor = tutor, navController = navController)
                }
            }
        }
    }
}

@Composable
fun TutorCard(tutor: User, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("tutorProfile/${tutor.username}") {
                    popUpTo(navController.graph.startDestinationId) { inclusive = false }
                    launchSingleTop = true
                    restoreState = true
                }
            }
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Text(
            text = tutor.username, // Ensure the `username` property exists in the `User` model
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )
    }
}
