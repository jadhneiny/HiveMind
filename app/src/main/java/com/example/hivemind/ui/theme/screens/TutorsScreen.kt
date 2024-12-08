package com.example.hivemind.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hivemind.models.User
import com.example.hivemind.network.ApiClient
import com.example.hivemind.navigation.BottomNavigationBar
import kotlinx.coroutines.launch

@Composable
fun TutorsScreen(navController: NavController, isTutor: Boolean) {
    val tutors = remember { mutableStateOf<List<User>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    // Fetch tutors from backend
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val response = ApiClient.apiService.getTutors()
                if (response.isSuccessful) {
                    tutors.value = response.body() ?: emptyList()
                } else {
                    println("Error fetching tutors: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("Exception fetching tutors: ${e.localizedMessage}")
            } finally {
                isLoading.value = false
            }
        }
    }

    // Screen layout with Scaffold
    Scaffold(
        containerColor = Color(0xFFAD42F7), // Set consistent background color
        bottomBar = { BottomNavigationBar(navController = navController, isTutor = isTutor) } // Include BottomNavigationBar
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFAD42F7))
                .padding(16.dp)
        ) {
            Text(
                text = "Available Tutors",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (isLoading.value) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else if (tutors.value.isEmpty()) {
                Text(
                    text = "No Tutors Available",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    tutors.value.forEach { tutor ->
                        TutorCard(tutor = tutor, onClick = {
                            navController.navigate("tutorProfile/${tutor.username}")
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun TutorCard(tutor: User, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column {
                Text(
                    text = tutor.username,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
                Text(
                    text = "Course: ${tutor.courseName ?: "Unknown"}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}
