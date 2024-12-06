package com.example.hivemind.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hivemind.ui.theme.screens.*
import com.example.hivemind.ui.screens.*
import com.example.hivemind.network.ApiClient
import com.example.hivemind.models.User
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment

@Composable
fun NavigationHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    isTutor: Boolean,
    currentUserId: Int // Add this parameter to pass the logged-in user's ID
) {
    NavHost(navController = navController, startDestination = Screen.Home.route, modifier = modifier) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Courses.route) { CoursesScreen(navController) }
        composable(Screen.Schedule.route) { ScheduleScreen() }

        if (!isTutor) {
            // Non-tutor specific routes
            composable(Screen.Tutors.route) { TutorsScreen(navController) }
        }

        composable(Screen.Chat.route) { ChatScreen(navController) }

        // Profile route for each tutor
        composable("tutorProfile/{tutorName}") { backStackEntry ->
            val tutorName = backStackEntry.arguments?.getString("tutorName") ?: "Unknown Tutor"

            var tutorProfile by remember { mutableStateOf<User?>(null) }
            var isLoading by remember { mutableStateOf(true) }
            var errorMessage by remember { mutableStateOf<String?>(null) }
            val coroutineScope = rememberCoroutineScope()

            LaunchedEffect(tutorName) {
                coroutineScope.launch {
                    try {
                        val response = ApiClient.apiService.getTutorByName(tutorName)
                        if (response.isSuccessful) {
                            tutorProfile = response.body()
                        } else {
                            errorMessage = "Failed to load tutor profile: ${response.errorBody()?.string()}"
                        }
                    } catch (e: Exception) {
                        errorMessage = "Error: ${e.localizedMessage}"
                    } finally {
                        isLoading = false
                    }
                }
            }

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                errorMessage != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = errorMessage ?: "Unknown error", color = MaterialTheme.colorScheme.error)
                    }
                }
                tutorProfile != null -> {
                    MyProfileScreen(
                        navController = navController,
                        isTutor = true,
                        courses = tutorProfile?.courseName?.let { listOf(it) } ?: emptyList(),
                        tutorName = tutorProfile?.username ?: "Unknown Tutor",
                        onChatClick = {
                            navController.navigate("chatDetail/${tutorProfile?.id}/${tutorProfile?.username}")
                        }
                    )
                }
            }
        }

        // Dynamic route for TutorsByCourse with courseName as parameter
        composable("tutorsByCourse/{courseName}") { backStackEntry ->
            val courseName = backStackEntry.arguments?.getString("courseName")
            TutorsByCourseScreen(courseName = courseName ?: "Unknown Course", navController = navController)
        }

        // Dynamic route for ChatDetail with tutorId, chatId, and displayName as parameters
        composable("chatDetail/{tutorId}/{chatId}/{displayName}") { backStackEntry ->
            val tutorId = backStackEntry.arguments?.getString("tutorId")?.toIntOrNull() ?: 0
            val chatId = backStackEntry.arguments?.getString("chatId")?.toIntOrNull() ?: 0
            val displayName = backStackEntry.arguments?.getString("displayName") ?: "Unknown"

            // Pass the currentUserId to ChatDetailScreen
            ChatDetailScreen(
                chatId = chatId,
                displayName = displayName,
                currentUserId = currentUserId
            )

        }
    }
}

