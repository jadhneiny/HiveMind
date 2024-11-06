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
    ).filter { it.second == courseName }

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

        tutors.forEach { (tutorName, _) ->
            Text(
                text = tutorName,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color.White.copy(alpha = 0.1f))
                    .clickable {
                        // Navigate to MyProfileScreen with selected tutor's name
                        navController.navigate("tutorProfile/$tutorName")
                    }
                    .padding(16.dp),
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }
    }
}
