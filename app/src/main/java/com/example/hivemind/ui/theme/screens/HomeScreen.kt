package com.example.hivemind.ui.theme.screens
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hivemind.R
import com.example.hivemind.models.Course
import com.example.hivemind.network.ApiClient
import com.example.hivemind.navigation.BottomNavigationBar
import com.example.hivemind.navigation.Screen
import kotlinx.coroutines.launch

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
    var courses by remember { mutableStateOf<List<Course>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var showModal by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val response = ApiClient.apiService.getCourses()
                if (response.isSuccessful) {
                    courses = response.body() ?: emptyList()
                    Log.d("HomeScreen", "Courses fetched: $courses")
                } else {
                    Log.e("HomeScreen", "Failed to fetch courses: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("HomeScreen", "Error fetching courses", e)
            } finally {
                isLoading = false
            }
        }
    }

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
        containerColor = Color(0xFF6200EE),
        bottomBar = {
            BottomNavigationBar(navController = navController, isTutor = isTutor)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .background(backgroundBrush)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo at the top
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "HiveMind Logo",
                modifier = Modifier.size(150.dp)
            )

            // Slideshow
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

            // Courses Section
            Text(
                text = if (isTutor) "My Courses" else "Available Courses",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp), color = Color.White)
            } else if (courses.isEmpty()) {
                Text(
                    text = "No courses available at the moment.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    items(courses) { course ->
                        CourseIcon(
                            title = course.name,
                            icon = Icons.AutoMirrored.Filled.MenuBook,
                            navController = navController
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action Cards
            EnhancedActionCard(
                title = "Schedule Planning",
                description = "Plan your study schedule.",
                icon = Icons.Default.Event,
                navController = navController,
                route = Screen.Schedule.route // Fixed navigation route
            )
            EnhancedActionCard(
                title = if (isTutor) "Chat" else "Chat with Tutor",
                description = if (isTutor) "Message your students or staff." else "Get help in real-time.",
                icon = Icons.AutoMirrored.Filled.Chat,
                navController = navController,
                route = Screen.Chat.route // Fixed navigation route
            )
        }
    }

    // Modal for menu
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
                HorizontalDivider(color = Color.Gray, thickness = 1.dp)
                Spacer(modifier = Modifier.height(16.dp))

                MenuItem(
                    title = "My Profile",
                    icon = Icons.Default.Person,
                    onClick = { navController.navigate("myProfile") }
                )
                MenuItem(
                    title = "Sign Out",
                    icon = Icons.AutoMirrored.Filled.ExitToApp,
                    onClick = {
                        navController.popBackStack()
                        navController.navigate("loginScreen")
                    }
                )
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
            .clickable {
                try {
                    navController.navigate(route)
                } catch (e: Exception) {
                    Log.e("EnhancedActionCard", "Navigation error: ${e.localizedMessage}")
                }
            },
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

@Composable
fun MenuItem(title: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title, tint = Color.Gray)
        Spacer(modifier = Modifier.width(8.dp))
        Text(title, color = Color.Gray, style = MaterialTheme.typography.bodyLarge)
    }
}

