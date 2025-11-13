package com.example.proyectobaselogin.vistas

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

// Data class para representar un evento del horario
data class ScheduleEvent(
    val title: String,
    val description: String,
    val time: String,
    val color: Color
)

// Lista de colores para elegir aleatoriamente para cada nuevo evento
private val eventColors = listOf(
    Color(0xFFFDB813),
    Color(0xFF8A8AFF),
    Color(0xFF3CE1E1),
    Color(0xFFFA8072),
    Color(0xFF81C784),
    Color(0xFFBA68C8)
)

/**
 * Un objeto Singleton que actúa como nuestra base de datos en memoria.
 * Utiliza `mutableStateListOf` para que Jetpack Compose reaccione automáticamente
 * a los cambios (adiciones, eliminaciones, etc.) y actualice la interfaz.
 */
object ScheduleRepository {
    val events = mutableStateListOf<ScheduleEvent>(
        // Datos iniciales de ejemplo
        ScheduleEvent("Alarma1", "Una alarma de prueba", "11:30", Color(0xFFFDB813)),
        ScheduleEvent("Comida para Firulais", "No olvidar denuevo a alimentar a Firulais", "13:00", Color(0xFF8A8AFF)),
    )

    // Función para añadir un nuevo evento a la lista
    fun addEvent(title: String, description: String, time: String) {
        events.add(
            ScheduleEvent(
                title = title,
                description = description,
                time = time,
                color = eventColors.random(Random(System.currentTimeMillis())) // Asigna un color aleatorio
            )
        )
    }
}