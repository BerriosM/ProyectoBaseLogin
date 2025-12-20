package com.example.peteat

import com.example.peteat.utils.InputValidation
import org.junit.Test
import org.junit.Assert.*

/**
 * Pruebas unitarias para verificar el cumplimiento de normas ISO de Seguridad.
 */
class IsoSecurityTest {
    
    /**
     * ISO A.14.1 - Validación de datos de entrada.
     * Se verifica que la política de contraseñas cumpla con los estándares de complejidad definidos:
     * - Mínimo 8 caracteres
     * - Al menos una mayúscula y una minúscula
     * - Al menos un número
     * - Al menos un carácter especial
     */
    @Test
    fun testIsoStrongPasswordPolicy() {
        // Caso positivo: Contraseña que cumple todos los requisitos
        assertTrue("La contraseña fuerte debería ser válida", InputValidation.isValidPassword("StrongP@ss1"))
        
        // Casos negativos (Auditoría de fallos):
        assertFalse("Contraseña sin mayúscula debería fallar", InputValidation.isValidPassword("weakp@ss1"))
        assertFalse("Contraseña sin minúscula debería fallar", InputValidation.isValidPassword("WEAKP@SS1"))
        assertFalse("Contraseña sin número debería fallar", InputValidation.isValidPassword("WeakP@ss"))
        assertFalse("Contraseña sin carácter especial debería fallar", InputValidation.isValidPassword("WeakPass12"))
        assertFalse("Contraseña demasiado corta debería fallar", InputValidation.isValidPassword("Sho1!"))
    }

    /**
     * ISO A.14.1 - Sanitización de datos.
     * Verificar que los inputs sean limpiados de espacios innecesarios antes de ser procesados.
     */
    @Test
    fun testIsoInputSanitization() {
        val rawInput = "   usuario@ejemplo.com   "
        val expected = "usuario@ejemplo.com"
        assertEquals("El input debe ser sanitizado (trim)", expected, InputValidation.sanitizeInput(rawInput))
    }
}
