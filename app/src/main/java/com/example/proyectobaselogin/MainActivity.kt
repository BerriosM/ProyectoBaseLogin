package com.example.proyectobaselogin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proyectobaselogin.ui.theme.PetEatTheme
import com.example.proyectobaselogin.vistas.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PetEatTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {

                        NavHost(navController = navController, startDestination = "login") {
                            composable("login") {
                                LoginScreen(navController)
                            }
                            composable("register") {
                                RegisterScreen(navController)
                            }
                            composable("home") {
                                HomeScreen(navController)
                            }
                            composable("schedules") {
                                SchedulesScreen(navController)
                            }
                            composable("add_schedule") {
                                AddScheduleScreen(navController)
                            }
                            composable("alarms") {
                                AlarmsScreen()
                            }
                            composable(
                                "edit_schedule/{scheduleId}",
                                arguments = listOf(navArgument("scheduleId") { type = NavType.StringType })
                            ) {
                                val scheduleId = it.arguments?.getString("scheduleId") ?: ""
                                EditScheduleScreen(navController, scheduleId)
                            }
                        }
                    }
                }
            }
        }
    }
}
