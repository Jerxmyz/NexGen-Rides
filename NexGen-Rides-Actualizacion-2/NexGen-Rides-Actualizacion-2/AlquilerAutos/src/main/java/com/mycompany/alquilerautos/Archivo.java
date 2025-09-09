package com.mycompany.alquilerautos;

import java.io.*;
import java.util.*;

public class Archivo {

    private static final String CLIENTES_CSV = "clientes.csv";
    private static final String VEHICULOS_CSV = "vehiculos.csv";
    private static final String ALQUILERES_CSV = "alquileres.csv";

    private static final String DELIM = ",";

    // CLIENTES
    public static void guardarClientes(List<Cliente> clientes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CLIENTES_CSV))) {
            writer.write("Nombre,Cedula,Correo,Direccion");
            writer.newLine();
            for (Cliente cliente : clientes) {
                String linea = String.join(DELIM,
                        cliente.getNombre(),
                        String.valueOf(cliente.getCedula()),
                        cliente.getCorreo(),
                        cliente.getDireccion());
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error guardando clientes: " + e.getMessage());
        }
    }

    public static List<Cliente> cargarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CLIENTES_CSV))) {
            reader.readLine(); // encabezado
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(DELIM);
                if (datos.length == 4) {
                    clientes.add(new Cliente(
                            datos[0],
                            Integer.parseInt(datos[1]),
                            datos[2],
                            datos[3]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error leyendo clientes: " + e.getMessage());
        }
        return clientes;
    }

    // VEHICULOS
    public static List<Vehiculo> cargarVehiculos() {
        List<Vehiculo> lista = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(VEHICULOS_CSV))) {
            reader.readLine(); // encabezado
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(DELIM);
                if (datos.length == 5) {
                    Vehiculo v = new Vehiculo(
                            datos[0],
                            datos[1],
                            datos[2],
                            Double.parseDouble(datos[3]),
                            datos[4]);
                    lista.add(v);
                }
            }
        } catch (IOException e) {
            System.err.println("Error leyendo vehiculos: " + e.getMessage());
        }
        return lista;
    }

    public static void guardarVehiculos(List<Vehiculo> vehiculos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(VEHICULOS_CSV))) {
            writer.write("Marca,Modelo,Nombre,ValorHora,Estado");
            writer.newLine();
            for (Vehiculo v : vehiculos) {
                writer.write(v.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error guardando vehiculos: " + e.getMessage());
        }
    }

    // ALQUILER
    public static void guardarAlquiler(Alquiler alquiler) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ALQUILERES_CSV, true))) {
            writer.write(alquiler.toCSV());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error guardando alquiler: " + e.getMessage());
        }
    }

    public static void mostrarHistorialAlquileres() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ALQUILERES_CSV))) {
            System.out.println("\n=== HISTORIAL DE ALQUILERES ===");
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.err.println("Error leyendo historial: " + e.getMessage());
        }
    }
}