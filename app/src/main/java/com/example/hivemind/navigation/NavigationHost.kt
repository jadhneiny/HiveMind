package com.example.hivemind.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hivemind.ui.screens.*
import com.example.hivemind.ui.screens.MyProfileScreen

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = Screen.Home.route, modifier = modifier) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Courses.route) { CoursesScreen(navController) }
        composable(Screen.Schedule.route) { ScheduleScreen() }
        composable(Screen.Tutors.route) { TutorsScreen(navController) }
        composable(Screen.Chat.route) { ChatScreen(navController) }

        // Profile route for each tutor
        composable("tutorProfile/{tutorName}") { backStackEntry ->
            val tutorName = backStackEntry.arguments?.getString("tutorName") ?: "Unknown Tutor"
            MyProfileScreen(
                navController = navController,
                isTutor = true,
                courses = listOf("Math", "Physics", "Chemistry"),  // Customize based on tutor data
                tutorName = tutorName,
                onChatClick = {
                    navController.navigate("chatDetail/$tutorName")
                }
            )
        }

        // Dynamic route for TutorsByCourse with courseName as parameter
        composable("tutorsByCourse/{courseName}") { backStackEntry ->
            val courseName = backStackEntry.arguments?.getString("courseName")
            TutorsByCourseScreen(courseName = courseName ?: "Unknown Course", navController = navController)
        }

        // Dynamic route for ChatDetail with tutorName as parameter
        composable("chatDetail/{tutorName}") { backStackEntry ->
            val tutorName = backStackEntry.arguments?.getString("tutorName")
            ChatDetailScreen(tutorName = tutorName ?: "Unknown Tutor")
        }
    }
}
