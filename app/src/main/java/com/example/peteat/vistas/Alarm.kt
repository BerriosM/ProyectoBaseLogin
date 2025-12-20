package com.example.peteat.vistas

data class Alarm(
    val id: String = "",
    val hour: Int = 0,
    val minute: Int = 0,
    val title: String = "" // Renombrado de 'label' a 'title'
)