package com.example.proyectobaselogin.vistas

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

class SchedulesViewModel : ViewModel() {

    private val database = Firebase.database.getReference("schedules")

    private val _schedules = MutableStateFlow<List<ScheduleEvent>>(emptyList())
    val schedules: StateFlow<List<ScheduleEvent>> = _schedules

    private val _selectedSchedule = MutableStateFlow<ScheduleEvent?>(null)
    val selectedSchedule: StateFlow<ScheduleEvent?> = _selectedSchedule

    // Lista de colores para elegir aleatoriamente
    private val eventColors = listOf(
        0xFFFDB813,
        0xFF8A8AFF,
        0xFF3CE1E1,
        0xFFFA8072,
        0xFF81C784,
        0xFFBA68C8
    )

    init {
        fetchSchedules()
    }

    private fun fetchSchedules() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val scheduleList = snapshot.children.mapNotNull { it.getValue(ScheduleEvent::class.java)?.copy(id = it.key ?: "") }
                _schedules.value = scheduleList
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar el error si es necesario
            }
        })
    }

    fun getScheduleById(id: String) {
        database.child(id).get().addOnSuccessListener {
            _selectedSchedule.value = it.getValue(ScheduleEvent::class.java)?.copy(id = it.key ?: "")
        }
    }

    fun addSchedule(title: String, description: String, time: String) {
        val id = database.push().key ?: return
        val colorValue = eventColors.random(Random(System.currentTimeMillis()))
        val schedule = ScheduleEvent(id, title, description, time, colorValue)
        database.child(id).setValue(schedule)
    }

    fun updateSchedule(id: String, title: String, description: String, time: String) {
        val scheduleUpdate = mapOf(
            "title" to title,
            "description" to description,
            "time" to time
        )
        database.child(id).updateChildren(scheduleUpdate)
    }

    fun deleteSchedule(scheduleId: String) {
        database.child(scheduleId).removeValue()
    }
}