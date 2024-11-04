package com.example.hivemind.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hivemind.navigation.Screen

data class Tutor(val name: String, val course: String)

@Composable
fun TutorsScreen(navController: NavController) {
    val tutors = listOf(
        Tutor("Alice Johnson", "Mathematics"),
        Tutor("Bob Smith", "Physics"),
        Tutor("Carol Davis", "Biology"),
        Tutor("David Wilson", "Computer Science"),
        Tutor("Eva Brown", "Chemistry")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF495A62)) // Consistent background color
            .padding(16.dp)
    ) {
        Text(
            text = "Available Tutors",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White, // Set text color to white for contrast
            modifier = Modifier.padding(bottom = 16.dp)
        )

        tutors.forEach { tutor ->
            TutorCard(tutor = tutor) {
                // Navigate to ChatDetailScreen for the selected tutor
                navController.navigate(Screen.ChatDetail.createRoute(tutor.name))
            }
        }
    }
}

@Composable
fun TutorCard(tutor: Tutor, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.1f) // Semi-transparent background for the card
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column {
                Text(
                    text = tutor.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White // Set text color to white for visibility
                )
                Text(
                    text = "Course: ${tutor.course}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f) // Slightly lighter color for secondary text
                )
            }
        }
    }
}
