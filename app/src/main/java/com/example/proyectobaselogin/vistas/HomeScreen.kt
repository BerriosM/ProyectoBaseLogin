package com.example.proyectobaselogin.vistas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel = viewModel()) {
    val topColor = Color(0xFF00A2E8)
    val bottomColor = Color(0xFF0A2444)
    var showDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val isBotonEnabled by homeViewModel.isBotonEnabled.collectAsState()
    val nextScheduleText by homeViewModel.nextScheduleText.collectAsState()
    val foodPercentage by homeViewModel.foodPercentage.collectAsState()
    val botonColor = if (isBotonEnabled) Color.Green else Color.Red
    var isMenuExpanded by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmar Cierre de Sesión") },
            text = { Text("¿Estás seguro de que quieres cerrar sesión?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        navController.navigate("login") {
                            popUpTo("home") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(topColor, bottomColor)
                )
            )
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        navigationIconContentColor = Color.White,
                        actionIconContentColor = Color.White
                    ),
                    navigationIcon = {
                        IconButton(onClick = { showDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Logout,
                                contentDescription = "Cerrar Sesión"
                            )
                        }
                    },
                    actions = {}
                )
            },
            floatingActionButton = {
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (isMenuExpanded) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                "Ingresar Horario",
                                color = Color.White,
                                modifier = Modifier
                                    .background(Color.Black.copy(alpha = 0.3f), shape = RoundedCornerShape(4.dp))
                                    .padding(8.dp)
                            )
                            FloatingActionButton(
                                onClick = { navController.navigate("add_schedule") },
                                containerColor = topColor
                            ) {
                                Icon(Icons.Default.Schedule, contentDescription = "Ingresar Horario")
                            }
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                "Ver Horarios",
                                color = Color.White,
                                modifier = Modifier
                                    .background(Color.Black.copy(alpha = 0.3f), shape = RoundedCornerShape(4.dp))
                                    .padding(8.dp)
                            )
                            FloatingActionButton(
                                onClick = { navController.navigate("schedules") },
                                containerColor = topColor
                            ) {
                                Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Ver Horarios")
                            }
                        }
                    }
                    FloatingActionButton(
                        onClick = { isMenuExpanded = !isMenuExpanded },
                        containerColor = topColor
                    ) {
                        Icon(
                            imageVector = if (isMenuExpanded) Icons.Default.Close else Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Inicio",
                    color = Color.White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 40.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = nextScheduleText,
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .background(
                            color = Color.Black.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Opciones",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(30.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .background(
                                    brush = Brush.verticalGradient(
                                        listOf(
                                            Color.White.copy(alpha = 0.4f),
                                            Color.Transparent
                                        )
                                    ),
                                    shape = CircleShape
                                )
                                .padding(4.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onPress = {
                                            try {
                                                homeViewModel.setBotonStatus(true)
                                                awaitRelease()
                                            } finally {
                                                homeViewModel.setBotonStatus(false)
                                            }
                                        }
                                    )
                                }
                        ) {
                            Card(
                                modifier = Modifier.size(180.dp),
                                shape = CircleShape,
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Download,
                                        contentDescription = "Descarga",
                                        modifier = Modifier.size(60.dp),
                                        tint = Color.Black
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Descarga",
                                        color = Color.Black,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }

                        Card(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = 12.dp, y = (-12).dp)
                                .size(40.dp),
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(containerColor = bottomColor.copy(alpha = 0.8f)),
                            border = BorderStroke(2.dp, Color.White.copy(alpha = 0.7f)),
                            onClick = { /* Acción de ejemplo */ }
                        ) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Filled.Circle,
                                    contentDescription = "Estado",
                                    tint = botonColor,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    // Comida para mascotas
                    Text(
                        text = "Comida para mascotas",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp) // Ajustar la altura según sea necesario
                    ) {
                        CircularProgressIndicator(
                            progress = { foodPercentage / 100f },
                            modifier = Modifier.size(150.dp),
                            color = topColor,
                            strokeWidth = 12.dp,
                            trackColor = Color.White.copy(alpha = 0.3f),
                            strokeCap = StrokeCap.Round
                        )
                        Text(
                            text = "$foodPercentage%",
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
