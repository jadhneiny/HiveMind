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
import androidx.navigation.NavController

@Composable
fun TutorsByCourseScreen(courseName: String, navController: NavController) {
    val tutors = listOf(
        "Alice Johnson" to "Mathematics",
        "Bob Smith" to "Physics",
        "Carol Davis" to "Biology",
        "David Wilson" to "Computer Science",
        "Eva Brown" to "Chemistry"
    ).filter { it.second == courseName } // Filter tutors by course

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF495A62)) // Consistent background color
            .padding(16.dp)
    ) {
        Text(
            text = "Tutors for $courseName",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White, // Set text color to white
            modifier = Modifier.padding(bottom = 16.dp)
        )

        tutors.forEach { (tutorName, _) ->
            Text(
                text = tutorName,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color.White.copy(alpha = 0.1f)) // Light background for each tutor item
                    .clickable {
                        // Navigate to ChatDetailScreen with selected tutor's name
                        navController.navigate("chatDetail/$tutorName")
                    }
                    .padding(16.dp), // Inner padding for readability
                style = MaterialTheme.typography.titleLarge,
                color = Color.White // Set text color to white
            )
        }
    }
}
