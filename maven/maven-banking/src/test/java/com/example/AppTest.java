package com.example;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    public void testLeerArchivo() {
        String rutaArchivo = "src/resources/transactions.txt";
        String contenido = App.leerArchivo(rutaArchivo);
        assertNotNull(contenido);
        assertTrue(contenido.contains("sara.palaciosc@urosario.edu.co"));
    }

    @Test
    public void testObtenerTransacciones() {
        String rutaArchivo = "src/resources/transactions.txt";
        String jsonData = App.leerArchivo(rutaArchivo);
        assertNotNull(jsonData);

        List<JSONObject> transacciones = App.obtenerTransacciones(jsonData, "sara.palaciosc@urosario.edu.co");
        assertFalse(transacciones.isEmpty());
        assertEquals("sara.palaciosc@urosario.edu.co", transacciones.get(0).getString("usuario"));
    }

    @Test
    public void testGenerarExtracto() {
        String usuario = "sara.palaciosc@urosario.edu.co";
        String rutaArchivo = "src/resources/transactions.txt";
        String jsonData = App.leerArchivo(rutaArchivo);
        assertNotNull(jsonData);

        List<JSONObject> transacciones = App.obtenerTransacciones(jsonData, usuario);
        assertFalse(transacciones.isEmpty());

        App.generarExtracto(usuario, transacciones);

        File archivo = new File(usuario + ".txt");
        assertTrue(archivo.exists());

        try {
            String contenido = Files.readString(Paths.get(usuario + ".txt"));
            assertTrue(contenido.contains("📄 Extracto bancario de sara.palaciosc@urosario.edu.co"));
            assertTrue(contenido.contains("📅 Fecha: 2025-03-13 17:41:06.330219"));
            assertTrue(contenido.contains("💳 Tipo: Deposit"));
            assertTrue(contenido.contains("💰 Balance: 400"));
        } catch (Exception e) {
            fail("No se pudo leer el archivo generado.");
        } finally {
            archivo.delete(); // Limpiar el archivo generado después de la prueba
        }
    }
}