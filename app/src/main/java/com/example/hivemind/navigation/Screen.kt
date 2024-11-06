package com.example.hivemind.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {

    // Static routes
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Courses : Screen("courses", "Courses", Icons.Default.MenuBook)
    object Schedule : Screen("schedule", "Schedule", Icons.Default.Event)
    object Tutors : Screen("tutors", "Tutors", Icons.Default.Person)
    object Chat : Screen("chat", "Chat", Icons.Default.Chat)
    object MyProfile : Screen("myProfile", "My Profile", Icons.Default.Person)
    object TutorProfile : Screen("tutorProfile/{tutorName}", "Tutor Profile", Icons.Default.Person) {
        // Helper function to create a route with a specific tutor's name
        fun createRoute(tutorName: String): String = "tutorProfile/$tutorName"
    }
    // Dynamic route for ChatDetail with a tutorName parameter
    object ChatDetail : Screen("chatDetail/{tutorName}", "Chat Detail", Icons.Default.Chat) {
        // Generates the complete route with a specific tutor's name
        fun createRoute(tutorName: String): String = "chatDetail/$tutorName"
    }
}
