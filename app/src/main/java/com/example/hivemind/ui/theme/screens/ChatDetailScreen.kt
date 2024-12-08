package com.example.hivemind.ui.theme.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import kotlinx.coroutines.launch
import com.example.hivemind.models.Message
import com.example.hivemind.network.ApiClient

@Composable
fun ChatDetailScreen(chatId: Int, displayName: String, currentUserId: Int) {
    var messages by remember { mutableStateOf<List<Message>>(emptyList()) }
    var currentMessage by remember { mutableStateOf(TextFieldValue("")) }
    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Fetch messages from the backend
    LaunchedEffect(chatId) {
        coroutineScope.launch {
            try {
                val response = ApiClient.apiService.getMessagesForChat(chatId)
                Log.d("ChatDetailScreen", "Request URL: https://hive-mind-backend.vercel.app/chats/$chatId/messages")
                if (response.isSuccessful) {
                    messages = response.body() ?: emptyList()
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
            text = "Chat with $displayName",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (isLoading) {
            CircularProgressIndicator(color = Color.White, modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (!errorMessage.isNullOrEmpty()) {
            Text(
                text = errorMessage!!,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Red,
                modifier = Modifier.padding(top = 16.dp)
            )
        } else {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                messages.forEach { message ->
                    val senderName = if (message.senderId == currentUserId) {
                        "You"
                    } else {
                        displayName // The other participant's name
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .background(Color.White.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp))
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "$senderName: ${message.content}",
                            color = Color.White
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = currentMessage,
                    onValueChange = { currentMessage = it },
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(color = Color.White)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        if (currentMessage.text.isNotBlank()) {
                            coroutineScope.launch {
                                try {
                                    val newMessage = Message(
                                        chatId = chatId,
                                        senderId = currentUserId,
                                        content = currentMessage.text,
                                        timestamp = "" // Timestamp will be generated by backend
                                    )
                                    val sendMessageResponse = ApiClient.apiService.sendMessage(newMessage)
                                    if (sendMessageResponse.isSuccessful) {
                                        messages = messages + sendMessageResponse.body()!!
                                        currentMessage = TextFieldValue("")
                                    } else {
                                        Log.e(
                                            "ChatDetailScreen",
                                            "Failed to send message: ${sendMessageResponse.code()}"
                                        )
                                    }
                                } catch (e: Exception) {
                                    Log.e("ChatDetailScreen", "Error sending message: ${e.localizedMessage}")
                                }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF64737D))
                ) {
                    Text("Send", color = Color.White)
                }
            }
        }
    }
}
