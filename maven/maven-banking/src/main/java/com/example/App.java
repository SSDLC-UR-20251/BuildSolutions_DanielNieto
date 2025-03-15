package com.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import org.json.*;

public class App {

    // 🔹 1. Leer el archivo JSON desde un .txt
    public static String leerArchivo(String rutaArchivo) {
        try {
            return Files.readString(Paths.get(rutaArchivo));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 🔹 2. Obtener transacciones de un usuario específico
    public static List<JSONObject> obtenerTransacciones(String jsonData, String usuario) {
        List<JSONObject> transacciones = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray transaccionesArray = jsonObject.getJSONArray(usuario);
            for (int i = 0; i < transaccionesArray.length(); i++) {
                transacciones.add(transaccionesArray.getJSONObject(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return transacciones;
    }

    // 🔹 3. Generar extracto bancario en un archivo .txt
    public static void generarExtracto(String usuario, List<JSONObject> transacciones) {
        try {
            FileWriter writer = new FileWriter(usuario + ".txt");
            writer.write("📄 Extracto bancario de " + usuario + "\n\n");
            for (JSONObject transaccion : transacciones) {
                writer.write("📅 Fecha: " + transaccion.getString("timestamp") + "\n");
                writer.write("💳 Tipo: " + transaccion.getString("type") + "\n");
                writer.write("💰 Balance: " + transaccion.getString("balance") + "\n\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String rutaArchivo = "src/resources/transactions.txt";
        String usuario = "sara.palaciosc@urosario.edu.co";

        // Leer el archivo JSON
        String jsonData = leerArchivo(rutaArchivo);
        if (jsonData == null) {
            System.out.println("Error al leer el archivo.");
            return;
        }

        // Obtener transacciones del usuario
        List<JSONObject> transacciones = obtenerTransacciones(jsonData, usuario);

        // Generar extracto bancario
        generarExtracto(usuario, transacciones);

        System.out.println("Extracto bancario generado para el usuario: " + usuario);
    }
}