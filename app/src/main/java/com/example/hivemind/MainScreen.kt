package com.example.hivemind

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.hivemind.navigation.BottomNavigationBar
import com.example.hivemind.navigation.NavigationHost

@Composable
fun MainScreen(isTutor: Boolean, currentUserId: Int) { // Add currentUserId parameter
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController, isTutor) }
    ) { innerPadding ->
        NavigationHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            isTutor = isTutor,
            currentUserId = currentUserId // Pass currentUserId to NavigationHost
        )
    }
}
