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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchedulesScreen(navController: NavController, schedulesViewModel: SchedulesViewModel = viewModel()) {
    val events by schedulesViewModel.schedules.collectAsState()
    val topColor = Color(0xFF00A2E8)
    val bottomColor = Color(0xFF0A2444)

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
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Horarios",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    ),
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate("add_schedule") },
                    containerColor = topColor,
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
            ) {
                items(events) { event ->
                    ScheduleItem(
                        event = event,
                        onDelete = { schedulesViewModel.deleteSchedule(event.id) },
                        onEdit = { navController.navigate("edit_schedule/${event.id}") }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun ScheduleItem(event: ScheduleEvent, onDelete: () -> Unit, onEdit: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = event.time,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(90.dp),
            textAlign = TextAlign.Center,
            color = Color.White.copy(alpha = 0.9f)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.3f)),
            onClick = onEdit
        ) {
            Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                Box(
                    modifier = Modifier
                        .width(10.dp)
                        .fillMaxHeight()
                        .background(event.color)
                )
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(event.title, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                        IconButton(onClick = onDelete, modifier = Modifier.size(24.dp)) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.White.copy(alpha = 0.7f))
                        }
                    }
                    if (event.description.isNotBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(event.description, fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f))
                    }
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
