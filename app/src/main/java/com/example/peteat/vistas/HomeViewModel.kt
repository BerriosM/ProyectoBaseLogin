package com.example.peteat.vistas

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Calendar

class HomeViewModel : ViewModel() {

    private val botonRef = Firebase.database.getReference("Boton")
    private val schedulesRef = Firebase.database.getReference("schedules")
    private val comidaRef = Firebase.database.getReference("NivelComida")

    private val _isBotonEnabled = MutableStateFlow(false)
    val isBotonEnabled: StateFlow<Boolean> = _isBotonEnabled

    private val _nextScheduleText = MutableStateFlow("No hay horarios programados.")
    val nextScheduleText: StateFlow<String> = _nextScheduleText

    private val _foodPercentage = MutableStateFlow(0)
    val foodPercentage: StateFlow<Int> = _foodPercentage

    init {
        fetchBotonStatus()
        fetchSchedules()
        fetchFoodPercentage()
    }

    private fun fetchFoodPercentage() {
        comidaRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _foodPercentage.value = snapshot.getValue(Int::class.java) ?: 0
            }

            override fun onCancelled(error: DatabaseError) {
                _foodPercentage.value = 0
            }
        })
    }

    private fun fetchBotonStatus() {
        botonRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _isBotonEnabled.value = snapshot.getValue(Boolean::class.java) ?: false
            }

            override fun onCancelled(error: DatabaseError) {
                _isBotonEnabled.value = false
            }
        })
    }

    private fun fetchSchedules() {
        schedulesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val scheduleList = snapshot.children.mapNotNull { it.getValue(ScheduleEvent::class.java) }
                updateNextScheduleText(scheduleList)
            }

            override fun onCancelled(error: DatabaseError) {
                _nextScheduleText.value = "Error al cargar horarios."
            }
        })
    }

    private fun updateNextScheduleText(schedules: List<ScheduleEvent>) {
        if (schedules.isEmpty()) {
            _nextScheduleText.value = "No hay horarios programados."
            return
        }

        val calendar = Calendar.getInstance()
        val nowInMinutes = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE)

        val schedulesInMinutes = schedules.mapNotNull { schedule ->
            try {
                val parts = schedule.time.split(":")
                val hour = parts[0].toInt()
                val minute = parts[1].toInt()
                (hour * 60 + minute) to schedule
            } catch (e: Exception) {
                null
            }
        }

        // Busca el próximo horario que sea posterior a la hora actual.
        val upcomingSchedule = schedulesInMinutes
            .filter { it.first > nowInMinutes }
            .minByOrNull { it.first }

        if (upcomingSchedule != null) {
            // Si se encuentra un horario, se muestra.
            _nextScheduleText.value = "El próximo horario es a las ${upcomingSchedule.second.time}"
        } else {
            // Si no hay más horarios para hoy, se muestra un mensaje indicándolo.
            _nextScheduleText.value = "No hay más horarios por hoy."
        }
    }

    fun setBotonStatus(isPressed: Boolean) {
        botonRef.setValue(isPressed)
    }
}