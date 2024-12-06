package com.example.hivemind

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.hivemind.network.ApiClient
import com.example.hivemind.ui.theme.HiveMindTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HiveMindTheme {
                LoginScreen()
            }
        }
    }
}

@Composable
fun LoginScreen() {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isTutor by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    Scaffold(
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFAD42F7))
                    .padding(innerPadding)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 24.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "HiveMind Logo",
                            modifier = Modifier
                                .size(220.dp)
                                .padding(bottom = 24.dp),
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Login as Tutor", color = Color.White)
                            Switch(
                                checked = isTutor,
                                onCheckedChange = { isTutor = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    uncheckedThumbColor = Color.Gray
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = username,
                            onValueChange = { username = it },
                            label = { Text("Username", color = Color.White) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            )
                        )

                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Password", color = Color.White) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                            )
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = {
                                if (username.isNotEmpty() && password.isNotEmpty()) {
                                    isLoading = true
                                    loginUser(username, password, context, isTutor) { success ->
                                        isLoading = false
                                        if (success) {
                                            context.startActivity(
                                                Intent(context, MainActivity::class.java)
                                            )
                                            (context as ComponentActivity).finish()
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "Invalid credentials. Please try again.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                } else {
                                    Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(text = if (isTutor) "Login as Tutor" else "Login")
                        }
                    }
                }
            }
        }
    )
}

fun loginUser(username: String, password: String, context: Context, isTutor: Boolean, callback: (Boolean) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = ApiClient.apiService.login(username, password)
            if (response.isSuccessful) {
                val loginResponse = response.body()
                if (loginResponse != null) {
                    saveUserLogin(loginResponse.access_token, isTutor, context)
                    withContext(Dispatchers.Main) { callback(true) }
                } else {
                    withContext(Dispatchers.Main) { callback(false) }
                }
            } else {
                withContext(Dispatchers.Main) { callback(false) }
            }

        } catch (e: Exception) {
            withContext(Dispatchers.Main) { callback(false) }
        }
    }
}

fun saveUserLogin(userId: Int, isTutor: Boolean, context: Context) {
    val sharedPreferences = context.getSharedPreferences("UserLoginPrefs", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putInt("userId", userId) // Save the user ID
        putBoolean("isTutor", isTutor) // Save the tutor status
        apply()
    }
    Log.d("LoginActivity", "Saved userId: $userId, isTutor: $isTutor")
}

