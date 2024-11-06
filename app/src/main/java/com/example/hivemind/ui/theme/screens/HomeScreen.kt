package com.example.hivemind.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hivemind.R
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("UserLoginPrefs", Context.MODE_PRIVATE)
    val isTutor = sharedPreferences.getBoolean("isTutor", false)
    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFFAD42F7), Color(0xFF6200EE))
    )
    val images = listOf(R.drawable.slideone, R.drawable.slidetwo)
    var showModal by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("HiveMind") },
                actions = {
                    IconButton(onClick = { showModal = true }) {
                        Icon(Icons.Default.Menu, contentDescription = "Open Menu")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFFAD42F7))
            )
        },
        containerColor = Color(0xFF6200EE)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .background(backgroundBrush)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())  // Enables vertical scrolling for overflowing content
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo at the top of the slideshow
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "HiveMind Logo",
                modifier = Modifier.size(150.dp)
            )

            // Slideshow Component
            LazyRow(horizontalArrangement = Arrangement.Center, modifier = Modifier.padding(vertical = 8.dp)) {
                items(images) { image ->
                    Image(
                        painter = painterResource(id = image),
                        contentDescription = "Slide Image",
                        modifier = Modifier
                            .width(300.dp)
                            .height(200.dp)
                            .padding(horizontal = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Title for Courses
            Text(
                text = if (isTutor) "My Courses" else "Available Courses",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            Row(modifier = Modifier.horizontalScroll(rememberScrollState()).padding(8.dp)) {
                if (!isTutor) {
                    CourseIcon(title = "English", icon = Icons.Default.MenuBook, navController = navController)
                    CourseIcon(title = "Mathematics", icon = Icons.Default.Calculate, navController = navController)
                    CourseIcon(title = "Computer Science", icon = Icons.Default.Computer, navController = navController)
                    CourseIcon(title = "Physics", icon = Icons.Default.Science, navController = navController)
                    CourseIcon(title = "Chemistry", icon = Icons.Default.Biotech, navController = navController)
                }
            }

            // New Action Cards with Enhanced Style
            EnhancedActionCard(
                title = "Schedule Planning",
                description = "Plan your study schedule.",
                icon = Icons.Default.Event,
                navController = navController,
                route = "ScheduleScreen"
            )
            EnhancedActionCard(
                title = if (isTutor) "Chat" else "Chat with Tutor",
                description = if (isTutor) "Message your students or staff." else "Get help in real-time.",
                icon = Icons.Default.Chat,
                navController = navController,
                route = "ChatScreen"
            )
        }
    }

    // Modal for Profile and Sign Out Options
    if (showModal) {
        ModalBottomSheet(onDismissRequest = { showModal = false }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Menu",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(8.dp)
                )
                Divider(color = Color.Gray, thickness = 1.dp)
                Spacer(modifier = Modifier.height(16.dp))

                // My Profile Button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("myProfile")  // Navigate to MyProfileScreen
                        }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Person, contentDescription = "My Profile", tint = Color.Gray)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("My Profile", color = Color.Gray, style = MaterialTheme.typography.bodyLarge)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Sign Out Button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.popBackStack()
                            navController.navigate("loginScreen")
                        }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.ExitToApp, contentDescription = "Sign Out", tint = Color.Gray)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Sign Out", color = Color.Gray, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

@Composable
fun CourseIcon(title: String, icon: ImageVector, navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(8.dp)
            .clickable { navController.navigate("tutorsByCourse/$title") }
    ) {
        Icon(icon, contentDescription = title, tint = Color.White, modifier = Modifier.size(40.dp))
        Text(text = title, color = Color.White)
    }
}

@Composable
fun EnhancedActionCard(title: String, description: String, icon: ImageVector, navController: NavController, route: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { navController.navigate(route) },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFAD42F7)),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(20.dp)
                .background(Color(0xFF8E24AA), RoundedCornerShape(12.dp))
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Icon(icon, contentDescription = title, tint = Color.White, modifier = Modifier.size(50.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = title, style = MaterialTheme.typography.titleLarge, color = Color.White)
                Text(text = description, style = MaterialTheme.typography.bodyMedium, color = Color.White.copy(alpha = 0.7f))
            }
        }
    }
}
