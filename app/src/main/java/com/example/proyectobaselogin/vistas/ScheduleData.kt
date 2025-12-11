package com.example.proyectobaselogin.vistas

import androidx.compose.ui.graphics.Color

// Data class para representar un evento del horario, adaptada para Firebase
data class ScheduleEvent(
    val id: String = "", // ID único para Firebase
    val title: String = "",
    val description: String = "",
    val time: String = "",
    val colorValue: Long = 0L // Almacena el color como un Long (ARGB)
) {
    // Excluye el campo de color de la serialización de Firebase
    @get:androidx.compose.runtime.Composable
    @get:androidx.compose.runtime.ReadOnlyComposable
    val color: Color
        get() = Color(colorValue)
}
