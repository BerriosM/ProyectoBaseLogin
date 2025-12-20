package com.example.peteat.vistas

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AlarmsViewModel : ViewModel() {

    private val database = Firebase.database.getReference("alarms")

    private val _alarms = MutableStateFlow<List<Alarm>>(emptyList())
    val alarms: StateFlow<List<Alarm>> = _alarms

    init {
        fetchAlarms()
    }

    private fun fetchAlarms() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val alarmsList = snapshot.children.mapNotNull { it.getValue(Alarm::class.java)?.copy(id = it.key ?: "") }
                _alarms.value = alarmsList
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

    fun addAlarm(hour: Int, minute: Int, title: String) {
        val id = database.push().key ?: return
        val alarm = Alarm(id, hour, minute, title)
        database.child(id).setValue(alarm)
    }

    fun deleteAlarm(alarmId: String) {
        database.child(alarmId).removeValue()
    }
}