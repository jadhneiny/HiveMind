package com.example.hivemind.ui.theme.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
    var tutors by remember { mutableStateOf<List<User>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

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
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                color = Color.White
            )
        } else if (errorMessage != null) {
            Text(
                text = errorMessage ?: "Unknown error.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                modifier = Modifier.padding(top = 16.dp)
            )
        } else {
            tutors.forEach { tutor ->
                Text(
                    text = tutor.username,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color.White.copy(alpha = 0.1f))
                        .clickable {
                            // Navigate to Tutor Profile Screen
                            navController.navigate("tutorProfile/${tutor.username}")
                        }
                        .padding(16.dp),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            }
        }
    }
}
