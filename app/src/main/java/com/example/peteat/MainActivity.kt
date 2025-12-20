package com.example.peteat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.peteat.ui.theme.Blue40
import com.example.peteat.ui.theme.PetEatTheme
import com.example.peteat.utils.NetworkMonitor
import com.example.peteat.vistas.*
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PetEatTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                // Monitor de red
                val context = LocalContext.current
                val networkMonitor = remember { NetworkMonitor(context) }
                val isConnected by networkMonitor.isConnected.collectAsState(initial = true)

                // Alerta de conexión perdida
                if (!isConnected) {
                    AlertDialog(
                        onDismissRequest = { /* No permitir cerrar tocando fuera */ },
                        // Simplificado: Argumentos posicionales (Icono, Descripción, Modificador)
                        icon = { Icon(Icons.Default.WifiOff, null, Modifier.size(60.dp)) },
                        title = { Text("Sin conexión a Internet") },
                        text = { Text("Verifica tu conexión para seguir usando la aplicación.") },
                        confirmButton = {
                            TextButton(onClick = { /* Acción opcional o dejar vacío */ }) {
                                Text("Reintentar")
                            }
                        }
                    )
                }

                // Define las pantallas que tendrán barra de navegación
                val bottomNavScreens = listOf("home", "schedules", "add_schedule")

                // A.9.2 Control de Acceso:
                // Verificar si existe una sesión activa para determinar el destino inicial.
                val auth = FirebaseAuth.getInstance()
                val startDestination = if (auth.currentUser != null) "home" else "login"

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (currentRoute in bottomNavScreens) {
                            BottomNavigationBar(navController = navController)
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(navController = navController, startDestination = startDestination) {
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

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Alarmas", Icons.Default.Notifications, "add_schedule"),
        BottomNavItem("Home", Icons.Default.Home, "home"),
        BottomNavItem("Horarios", Icons.Default.DateRange, "schedules"),
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Blue40
                )
            )
        }
    }
}
