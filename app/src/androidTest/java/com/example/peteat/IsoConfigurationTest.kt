package com.example.peteat

import android.security.NetworkSecurityPolicy
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

/**
 * Pruebas de Sistema (Instrumentadas) para validar configuraciones de seguridad ISO.
 * Estas pruebas se ejecutan en el emulador o dispositivo físico.
 */
@RunWith(AndroidJUnit4::class)
class IsoConfigurationTest {

    /**
     * ISO A.14.1.2 - Seguridad de los servicios de las aplicaciones en redes públicas.
     * Norma: La información en tránsito debe estar protegida.
     * 
     * Esta prueba verifica que la aplicación NO permita tráfico en texto plano (HTTP).
     * Si la prueba falla, significa que tu app es vulnerable a ataques de intercepción (Man-in-the-Middle).
     */
    @Test
    fun testIsoSecureNetworkTraffic() {
        val networkPolicy = NetworkSecurityPolicy.getInstance()
        
        // Verificamos que el tráfico cleartext (HTTP no seguro) esté DESHABILITADO.
        // Si tu app necesita HTTP para algo específico, debe ser una excepción documentada, no la regla general.
        assertFalse(
            "Violación de seguridad: La aplicación permite tráfico HTTP no cifrado.", 
            networkPolicy.isCleartextTrafficPermitted()
        )
    }
}
