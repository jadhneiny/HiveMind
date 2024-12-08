package com.example.hivemind.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background // Import for the background modifier
import androidx.compose.ui.graphics.Color // Import for the Color class


import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ScheduleScreen() {
    var courseName by remember { mutableStateOf("") }
    var fromTime by remember { mutableStateOf("") }
    var toTime by remember { mutableStateOf("") }
    var scheduleList by remember { mutableStateOf(listOf<Pair<String, String>>()) }

    // Function to add course to schedule
    fun addCourse() {
        if (courseName.isNotEmpty() && fromTime.isNotEmpty() && toTime.isNotEmpty()) {
            scheduleList = scheduleList + Pair(courseName, "$fromTime - $toTime")
            courseName = ""
            fromTime = ""
            toTime = ""
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFAD42F7)), // Set the background color
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Title
            Text(
                text = "Schedule Planning Screen",
                style = MaterialTheme.typography.headlineMedium.copy(color = Color.White), // Title in white
                modifier = Modifier.padding(bottom = 16.dp)
            )

// Course Name Input
            Text("Course Name", color = Color.White)
            BasicTextField(
                value = courseName,
                onValueChange = { courseName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .background(Color.White, shape = MaterialTheme.shapes.small) // Input field background color
            )

// From Time Input
            Text("From Time", color = Color.White)
            BasicTextField(
                value = fromTime,
                onValueChange = { fromTime = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .background(Color.White, shape = MaterialTheme.shapes.small) // Input field background color
            )

// To Time Input
            Text("To Time", color = Color.White)
            BasicTextField(
                value = toTime,
                onValueChange = { toTime = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .background(Color.White, shape = MaterialTheme.shapes.small) // Input field background color
            )
// Add Course Button
            Button(
                onClick = { addCourse() },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth() // Make button take full width
                    .height(48.dp), // Adjust button height
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(text = "Add Course", color = Color(0xFF6200EE)) // Button text color
            }

// Schedule Table
            Spacer(modifier = Modifier.height(32.dp))

// Schedule Header
            Text("Your Schedule", style = MaterialTheme.typography.titleMedium.copy(color = Color.White))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                scheduleList.forEach { course ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(Color.White, shape = MaterialTheme.shapes.small) // Row background
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(course.first, modifier = Modifier.weight(1f), color = Color(0xFF6200EE)) // Course name color
                        Text(course.second, modifier = Modifier.weight(1f), color = Color.Gray) // Time color
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScheduleScreenPreview() {
    ScheduleScreen()
}