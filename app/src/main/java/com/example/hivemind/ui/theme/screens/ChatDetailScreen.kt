package com.example.hivemind.ui.screens

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

@Composable
fun ChatDetailScreen(tutorName: String) {
    var messages by remember { mutableStateOf(listOf("Hello! How can I help you today?", "I'm here to assist with any questions on $tutorName's course.")) }
    var currentMessage by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFAD42F7)) // Consistent background color
            .padding(16.dp)
    ) {
        Text(
            text = "Chat with $tutorName",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White, // White text color for contrast
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Display chat messages
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            messages.forEach { message ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .background(Color.White.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp)) // Semi-transparent message bubble
                        .padding(8.dp)
                ) {
                    Text(
                        text = message,
                        color = Color.White // White text color for readability
                    )
                }
            }
        }

        // Input field and send button
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
                    .background(Color.White.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp)) // Semi-transparent input background
                    .padding(8.dp),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(color = Color.White) // White text for input
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (currentMessage.text.isNotBlank()) {
                        messages = messages + "You: ${currentMessage.text}"
                        currentMessage = TextFieldValue("")
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF64737D)) // Styled button color
            ) {
                Text("Send", color = Color.White)
            }
        }
    }
}
