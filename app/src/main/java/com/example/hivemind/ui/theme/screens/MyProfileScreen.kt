package com.example.hivemind.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person

@OptIn(ExperimentalMaterial3Api::class)  // Opt-in to use Experimental Material3 APIs
@Composable
fun MyProfileScreen(
    navController: NavController,
    isTutor: Boolean,
    courses: List<String>,
    tutorName: String,  // Tutor name as a parameter
    onChatClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Profile of $tutorName") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display profile picture (replace with Image if available)
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile Picture",
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Display name and role
            Text(text = tutorName, style = MaterialTheme.typography.headlineMedium)
            Text(text = if (isTutor) "Tutor" else "Student", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(24.dp))

            // Display courses
            Text(text = "Courses:", style = MaterialTheme.typography.titleLarge)
            courses.forEach { course ->
                Text(text = "- $course", style = MaterialTheme.typography.bodyLarge)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Chat button
            Button(onClick = onChatClick) {
                Text(text = "Chat with $tutorName")
            }
        }
    }
}
