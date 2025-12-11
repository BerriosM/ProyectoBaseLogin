package com.example.proyectobaselogin.vistas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmsScreen(alarmsViewModel: AlarmsViewModel = viewModel()) {
    val alarms by alarmsViewModel.alarms.collectAsState()
    var hour by remember { mutableStateOf(0) }
    var minute by remember { mutableStateOf(0) }
    var title by remember { mutableStateOf("") } // Renombrado de 'label' a 'title'

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Añadir Alarma", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título") }, // Etiqueta actualizada
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = hour.toString(),
                onValueChange = { hour = it.toIntOrNull() ?: 0 },
                label = { Text("Hora") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = minute.toString(),
                onValueChange = { minute = it.toIntOrNull() ?: 0 },
                label = { Text("Minuto") },
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { alarmsViewModel.addAlarm(hour, minute, title) }, // Llama a la función con 'title'
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Añadir Alarma")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(alarms) { alarm ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "${alarm.hour}:${alarm.minute} - ${alarm.title}") // Muestra el título
                    Button(onClick = { alarmsViewModel.deleteAlarm(alarm.id) }) {
                        Text("Eliminar")
                    }
                }
            }
        }
    }
}
