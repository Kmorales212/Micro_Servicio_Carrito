package com.example.Micro_Servicio_Carrito.testUnitarios;

import com.example.Micro_Servicio_Carrito.configuration.SwaggerConfig;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwaggerConfigTest {

    @Test
    void testCustomOpenAPI_FullCoverage() {
        SwaggerConfig swaggerConfig = new SwaggerConfig();

        OpenAPI openAPI = swaggerConfig.customOpenAPI();

        assertNotNull(openAPI, "El objeto OpenAPI no debe ser nulo");
        assertNotNull(openAPI.getInfo(), "El objeto Info no debe ser nulo");
        assertEquals("API Microservicio Carrito", openAPI.getInfo().getTitle());
        assertEquals("1.0", openAPI.getInfo().getVersion());
        assertEquals("Documentación para gestionar el Carrito de Compras", openAPI.getInfo().getDescription());

        assertNotNull(openAPI.getSecurity(), "La lista de seguridad no debe ser nula");
        assertFalse(openAPI.getSecurity().isEmpty());
        assertTrue(openAPI.getSecurity().get(0).containsKey("BearerAuth"));

        assertNotNull(openAPI.getComponents());
        assertNotNull(openAPI.getComponents().getSecuritySchemes());
        
        SecurityScheme authScheme = openAPI.getComponents().getSecuritySchemes().get("BearerAuth");
        assertNotNull(authScheme);
        assertEquals(SecurityScheme.Type.HTTP, authScheme.getType());
        assertEquals("bearer", authScheme.getScheme());
        assertEquals("JWT", authScheme.getBearerFormat());
    }
}
