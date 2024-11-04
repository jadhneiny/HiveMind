package com.example.hivemind.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// Define a list of tutors with whom a chat has been started
val activeChats = listOf(
    Tutor("Alice Johnson", "Mathematics"),
    Tutor("Bob Smith", "Physics"),
    Tutor("Carol Davis", "Biology")
)

@Composable
fun ChatScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF495A62)) // Set consistent background color
            .padding(16.dp)
    ) {
        Text(
            text = "Active Chats",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White, // Set text color to white for contrast
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Display each active chat
        activeChats.forEach { tutor ->
            ChatListItem(tutor = tutor) {
                // Navigate to ChatDetailScreen to continue the conversation
                navController.navigate("chatDetail/${tutor.name}")
            }
        }
    }
}

@Composable
fun ChatListItem(tutor: Tutor, onClick: () -> Unit) {
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
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
