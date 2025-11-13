package com.example.proyectobaselogin.vistas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchedulesScreen(navController: NavController) {
    // Obtenemos la lista de eventos del repositorio central
    val events = ScheduleRepository.events

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Horarios Registrados", // Título actualizado
                        fontSize = 32.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Normal
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        // 1. Movemos el FAB a la posición central del Scaffold
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_schedule") },
                containerColor = Color(0xFF6A1B9A),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
                actions = {
                    // Dejamos espacio para el FAB en el centro
                    Spacer(modifier = Modifier.weight(1f, true))
                }
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
        ) {
            // 2. Pasamos la lógica de borrado a cada item
            items(events) { event ->
                ScheduleItem(
                    event = event,
                    onDelete = { ScheduleRepository.events.remove(event) } // Llama al remove de la lista
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ScheduleItem(event: ScheduleEvent, onDelete: () -> Unit) { // 3. Añadimos el callback onDelete
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = event.time,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(90.dp),
            textAlign = TextAlign.Center,
            color = Color.Black.copy(alpha = 0.8f)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = event.color.copy(alpha = 0.7f))
        ) {
            Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                Box(
                    modifier = Modifier
                        .width(8.dp)
                        .fillMaxHeight()
                        .background(event.color)
                )
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    // 4. Añadimos el botón de borrado junto al título
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(event.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                        IconButton(onClick = onDelete, modifier = Modifier.size(24.dp)) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Black.copy(alpha = 0.6f))
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(event.description, fontSize = 14.sp, color = Color.Black.copy(alpha = 0.7f))
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SchedulesScreenPreview() {
    SchedulesScreen(rememberNavController())
}
