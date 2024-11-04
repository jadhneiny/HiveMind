package com.example.hivemind.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.navigation.NavController

@Composable
fun CoursesScreen(navController: NavController) {
    val courses = listOf("Mathematics", "Physics", "Chemistry", "Biology", "Computer Science")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF495A62)) // Apply consistent background color
            .padding(16.dp)
    ) {
        Text(
            text = "Available Courses",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White // Set text color to white for contrast
        )

        Spacer(modifier = Modifier.height(16.dp))

        courses.forEach { course ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        // Navigate to TutorsByCourseScreen with selected course
                        navController.navigate("tutorsByCourse/$course")
                    },
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.1f) // Semi-transparent card background
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Text(
                    text = course,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White // Set text color to white for visibility
                )
            }
        }
    }
}
