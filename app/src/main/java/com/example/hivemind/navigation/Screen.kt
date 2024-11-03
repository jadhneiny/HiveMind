package com.example.hivemind.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Courses : Screen("courses", "Courses", Icons.Default.MenuBook)
    object Schedule : Screen("schedule", "Schedule", Icons.Default.Event)
    object Tutors : Screen("tutors", "Tutors", Icons.Default.Person)
    object Chat : Screen("chat", "Chat", Icons.Default.Chat)
}