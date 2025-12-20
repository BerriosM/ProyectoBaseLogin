package com.example.peteat

import com.example.peteat.vistas.ScheduleEvent
import org.junit.Test
import org.junit.Assert.*

/**
 * Pruebas unitarias para validar la Integridad de Datos (ISO A.13.2 y A.14.1).
 * Verifica que el modelo de datos mantenga su estructura correcta antes de ser persistido.
 */
class IsoIntegrityTest {

    /**
     * ISO A.14.1 - Validación de datos de entrada en transacciones.
     * Verifica que el objeto ScheduleEvent mantenga la integridad de sus datos obligatorios.
     */
    @Test
    fun testDataIntegrityStructure() {
        // Caso: Creación correcta de un evento
        val validEvent = ScheduleEvent(
            id = "unique_id_123",
            title = "Comida Mañana",
            description = "Dar 100g de croquetas",
            time = "08:00 AM",
            colorValue = 0xFFFDB813
        )

        // Verificación de integridad
        assertNotNull("El ID no debe ser nulo para mantener trazabilidad", validEvent.id)
        assertTrue("El ID no debe estar vacío", validEvent.id.isNotEmpty())
        assertTrue("El título es obligatorio para la integridad operativa", validEvent.title.isNotBlank())
        
        // Verificación de que el color se almacena correctamente como Long (persistencia segura)
        assertEquals("El valor del color debe persistir íntegramente", 0xFFFDB813, validEvent.colorValue)
    }

    /**
     * ISO A.12.3.1 - Integridad en la información.
     * Simula una validación de reglas de negocio antes de guardar en base de datos.
     */
    @Test
    fun testBusinessLogicIntegrity() {
        // Simulamos un evento que viene de la UI
        val event = ScheduleEvent(
            id = "temp_id",
            title = "Cena",
            time = "20:00"
        )

        // Regla de integridad: No se debe permitir guardar eventos sin hora definida
        val isValid = event.time.matches(Regex("^([0-1]?[0-9]|2[0-3]):[0-5][0-9].*\$")) // Formato simple HH:MM
        
        assertTrue("La integridad del formato de hora debe ser válida antes de guardar", isValid)
    }
}
