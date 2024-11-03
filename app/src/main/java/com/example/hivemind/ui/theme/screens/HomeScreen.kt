package com.example.hivemind.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hivemind.navigation.Screen

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(
            text = "Welcome to HiveMind!",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Explore our offerings:",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))

        // List of offerings
        OfferingCard(
            title = "Courses",
            description = "Access a variety of courses.",
            icon = Icons.Default.MenuBook,
            onClick = { navController.navigate(Screen.Courses.route) }
        )
        OfferingCard(
            title = "Schedule Planning",
            description = "Plan your study schedule.",
            icon = Icons.Default.Event,
            onClick = { navController.navigate(Screen.Schedule.route) }
        )
        OfferingCard(
            title = "Tutors",
            description = "Connect with expert tutors.",
            icon = Icons.Default.Person,
            onClick = { navController.navigate(Screen.Tutors.route) }
        )
        OfferingCard(
            title = "Chat with Tutors",
            description = "Get help in real-time.",
            icon = Icons.Default.Chat,
            onClick = { navController.navigate(Screen.Chat.route) }
        )
    }
}

@Composable
fun OfferingCard(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = title,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = title, style = MaterialTheme.typography.titleLarge)
                Text(text = description, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}