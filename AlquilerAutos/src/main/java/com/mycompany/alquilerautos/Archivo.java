package com.mycompany.alquilerautos;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Archivo {
  
    private static final String CSV_HEADER = "Nombre\tCedula\tCorreo\tDirección";
    private static final String CSV_FILE = "clientes.csv";
    private static final String DELIMITER = "\t";
    
    public static void guardarClientes(List<Cliente> clientes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE))) {
            writer.write(CSV_HEADER);
            writer.newLine();
            
            for (Cliente cliente : clientes) {
                String linea = String.join(DELIMITER,
                        cliente.getNombre(),
                        String.valueOf(cliente.getCedula()),
                        cliente.getCorreo(),
                        cliente.getDireccion());
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error guardando archivo: " + e.getMessage());
        }
    }
    
     public static List<Cliente> cargarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            // Saltar header
            reader.readLine();
            
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(DELIMITER);
                if (datos.length == 4) {
                    Cliente cliente = new Cliente(
                            datos[0],
                            Integer.parseInt(datos[1]),
                            datos[2],
                            datos[3]);
                    clientes.add(cliente);
                }
            }
        } catch (IOException e) {
            System.err.println("Error leyendo archivo: " + e.getMessage());
        }
        return clientes;
    }
     
     
}

    

