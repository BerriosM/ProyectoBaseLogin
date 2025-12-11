package com.example.proyectobaselogin.vistas

import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScheduleScreen(navController: NavController, scheduleId: String, schedulesViewModel: SchedulesViewModel = viewModel()) {
    val context = LocalContext.current

    // Observa el horario seleccionado del ViewModel
    val schedule by schedulesViewModel.selectedSchedule.collectAsState()

    var eventTitle by remember { mutableStateOf("") }
    var eventDescription by remember { mutableStateOf("") }
    var eventTime by remember { mutableStateOf("") }

    // Carga los datos del horario cuando la pantalla se muestra por primera vez
    LaunchedEffect(scheduleId) {
        schedulesViewModel.getScheduleById(scheduleId)
    }

    // Actualiza los estados locales cuando el horario del ViewModel cambia
    LaunchedEffect(schedule) {
        schedule?.let {
            eventTitle = it.title
            eventDescription = it.description
            eventTime = it.time
        }
    }

    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    val timePickerDialog = TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->
            eventTime = String.format("%02d:%02d", selectedHour, selectedMinute)
        }, hour, minute, true
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Horario") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { timePickerDialog.show() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = eventTime,
                    fontSize = 64.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = eventTitle,
                onValueChange = { eventTitle = it },
                label = { Text("Título del evento") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (eventTitle.isNotBlank()) {
                        // Llama a la función de actualización del ViewModel
                        schedulesViewModel.updateSchedule(scheduleId, eventTitle, eventDescription, eventTime)
                        navController.popBackStack()
                    } else {
                        Toast.makeText(context, "El título no puede estar vacío", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BFFF)),
            ) {
                Text("Guardar Cambios", color = Color.White)
            }
        }
    }
}
