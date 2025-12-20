package com.example.peteat.utils

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics

/**
 * Implementación de A.12.4 Auditoría y Registro.
 * Centraliza el registro de eventos de seguridad y operativos.
 */
object AuditLogger {
    
    fun logEvent(context: Context, eventName: String, params: Map<String, String> = emptyMap()) {
        val analytics = FirebaseAnalytics.getInstance(context)
        val bundle = Bundle()
        params.forEach { (key, value) ->
            bundle.putString(key, value)
        }
        analytics.logEvent(eventName, bundle)
        
        // Logcat para pruebas locales (ISO A.12.4)
        Log.d("ISO_AUDIT", "Event: $eventName, Params: $params")
    }

    fun logSecurityEvent(context: Context, action: String, status: String, user: String = "anonymous", details: String = "") {
        val params = mapOf(
            "action" to action,
            "status" to status,
            "user" to user,
            "details" to details,
            "timestamp" to System.currentTimeMillis().toString()
        )
        logEvent(context, "security_audit", params)
    }
}
