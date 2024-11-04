package com.example.hivemind.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hivemind.R
import com.example.hivemind.navigation.Screen

@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF495A62)) // Background color for consistency
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo at the top
            Image(
                painter = painterResource(id = R.drawable.hivemind), // Replace with actual logo resource ID
                contentDescription = "HiveMind Logo",
                modifier = Modifier
                    .size(220.dp)
                    .padding(bottom = 24.dp),

            )

            Text(
                text = "Welcome to HiveMind!",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Explore our offerings:",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
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
}

@Composable
fun OfferingCard(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent // Fully transparent background
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = title,
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = title, style = MaterialTheme.typography.titleLarge, color = Color.White)
                Text(text = description, style = MaterialTheme.typography.bodyMedium, color = Color.White.copy(alpha = 0.8f))
            }
        }
    }
}
