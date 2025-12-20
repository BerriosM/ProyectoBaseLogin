package com.example.peteat.utils

import android.util.Patterns

object InputValidation {
    fun isValidEmail(email: String): Boolean {
        return email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        // Ejemplo de validación fuerte:
        // Mínimo 8 caracteres, al menos una mayúscula, una minúscula, un número y un caracter especial
        val passwordPattern = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=!])(?=\\S+\$).{8,}\$")
        return password.matches(passwordPattern)
    }

    fun sanitizeInput(input: String): String {
        // Implementación básica de sanitización (ej. remover espacios extras)
        return input.trim()
    }
}
