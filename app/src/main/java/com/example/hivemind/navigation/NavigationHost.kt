package com.example.hivemind.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hivemind.ui.screens.*

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = Screen.Home.route, modifier = modifier) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Courses.route) { CoursesScreen() }
        composable(Screen.Schedule.route) { ScheduleScreen() }
        composable(Screen.Tutors.route) { TutorsScreen() }
        composable(Screen.Chat.route) { ChatScreen() }
    }
}