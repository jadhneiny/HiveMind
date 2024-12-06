package com.example.hivemind.ui.theme.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment // Add this import
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hivemind.models.Course
import com.example.hivemind.network.ApiClient
import kotlinx.coroutines.launch

@Composable
fun CoursesScreen(navController: NavController) {
    var courses by remember { mutableStateOf<List<Course>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val response = ApiClient.apiService.getCourses()
                if (response.isSuccessful) {
                    courses = response.body() ?: emptyList()
                    Log.d("CoursesScreen", "Courses fetched: $courses")
                } else {
                    Log.e("CoursesScreen", "Failed to fetch courses: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("CoursesScreen", "Error fetching courses", e)
            } finally {
                isLoading = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFAD42F7))
            .padding(16.dp)
    ) {
        Text(
            text = "Available Courses",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center // Use Alignment.Center for loading indicator
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        } else if (courses.isEmpty()) {
            Text(
                text = "No courses available at the moment.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(courses) { course ->
                    CourseCard(course = course, navController = navController)
                }
            }
        }
    }
}

@Composable
fun CourseCard(course: Course, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                navController.navigate("tutorsByCourse/${course.name}")
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Text(
            text = course.name,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )
    }
}
