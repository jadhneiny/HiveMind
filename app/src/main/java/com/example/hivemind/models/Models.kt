package com.example.hivemind.models

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    val username: String,
    val email: String,
    @SerializedName("is_tutor") val isTutor: Boolean,
    @SerializedName("course_name") val courseName: String? = null
)



data class Chat(
    val id: Int,
    @SerializedName("tutor_id") val tutorId: Int,
    @SerializedName("tutor_name") val tutorName: String?,
    @SerializedName("student_id") val studentId: Int,
    @SerializedName("student_name") val studentName: String?,
    @SerializedName("created_at") val createdAt: String
)


data class Message(
    val id: Int? = null, // Make id nullable and optional
    @SerializedName("chat_id") val chatId: Int,
    @SerializedName("sender_id") val senderId: Int,
    val content: String,
    val timestamp: String
)


data class Course(
    val id: Int,
    val name: String,
    val description: String? = null
)

