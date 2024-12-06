package com.example.hivemind.network

data class LoginResponse(
    val id: Int,
    val username: String,
    val email: String,
    val isTutor: Boolean,
    val userId: Int,
    val access_token: Int,
    val token_type: String
)
