package com.example.hivemind.ui.theme.screens

import android.content.Context
import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hivemind.models.Chat
import com.example.hivemind.network.ApiClient
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext

@Composable
fun ChatScreen(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    var activeChats by remember { mutableStateOf<List<Chat>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("UserLoginPrefs", Context.MODE_PRIVATE)
    val userId = sharedPreferences.getInt("userId", -1)
    val isTutor = sharedPreferences.getBoolean("isTutor", false)

    if (userId == -1) {
        errorMessage = "Error: User ID not found. Please log in again."
        return
    }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val response = ApiClient.apiService.getUserChats(userId)
                if (response.isSuccessful) {
                    activeChats = response.body() ?: emptyList()
                } else {
                    errorMessage = "Error: ${response.code()} - ${response.message()}"
                }
            } catch (e: Exception) {
                errorMessage = "Error: ${e.localizedMessage ?: "Unknown error"}"
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
            text = "Active Chats",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else if (!errorMessage.isNullOrEmpty()) {
            Text(
                text = errorMessage!!,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Red,
                modifier = Modifier.padding(top = 16.dp)
            )
        } else if (activeChats.isEmpty()) {
            Text(
                text = "No Active Chats",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 16.dp)
            )
        } else {
            activeChats.forEach { chat ->
                ChatListItem(chat = chat, isTutor = isTutor, navController = navController)
            }
        }
    }
}

@Composable
fun ChatListItem(chat: Chat, isTutor: Boolean, navController: NavController) {
    val displayName = if (isTutor) chat.studentName else chat.tutorName

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                navController.navigate("chatDetail/${chat.tutorId}/${chat.id}/${displayName}")
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = if (isTutor) "Chat with Student: $displayName" else "Chat with Tutor: $displayName",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
                Text(
                    text = "Created At: ${chat.createdAt}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}
