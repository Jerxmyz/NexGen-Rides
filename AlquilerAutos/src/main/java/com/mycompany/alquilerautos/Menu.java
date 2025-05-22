package com.mycompany.alquilerautos;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Menu {
    private Scanner scanner;
    private List<Cliente> clientes;
    
    public Menu() {
        scanner = new Scanner(System.in);
        clientes = new ArrayList<>();
        cargarDatosIniciales();
    }
    
    private void cargarDatosIniciales() {
        List<Cliente> datosGuardados = Archivo.cargarClientes();
        if(!datosGuardados.isEmpty()) {
            clientes.addAll(datosGuardados);
            System.out.println("Datos cargados correctamente!");
        }
    }
    
    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n=== SISTEMA DE CLIENTES ===");
            System.out.println("1. Registrar nuevo cliente");
            System.out.println("2. Mostrar todos los clientes");
            System.out.println("3. Buscar cliente por cédula");
            System.out.println("4. Editar cliente");
            System.out.println("5. Guardar y salir");
            System.out.print("Seleccion: ");
            
            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                
                switch(opcion) {
                    case 1:
                        registrarCliente();
                        break;
                    case 2:
                        mostrarClientes();
                        break;
                    case 3:
                        buscarCliente();
                        break;
                    case 4:
                        editarCliente();
                        break;
                    case 5:
                        guardarYSalir();
                        break;
                    default:
                        System.out.println("Opción no válida!");
                }
            } catch(InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número!");
                scanner.nextLine(); // Limpiar entrada inválida
                opcion = 0;
            }
            
        } while(opcion != 5);
    }
    
    private void registrarCliente() {
        Cliente nuevoCliente = new Cliente("", 0, "", "");
        nuevoCliente.pedirDatos();
        
        if(cedulaExiste(nuevoCliente.getCedula())) {
            System.out.println("Error: La cédula ya está registrada!");
            return;
        }
        
        clientes.add(nuevoCliente);
        System.out.println("Cliente registrado exitosamente!");
    }
    
    private boolean cedulaExiste(int cedula) {
        return clientes.stream().anyMatch(c -> c.getCedula() == cedula);
    }
    
    private void mostrarClientes() {
        if(clientes.isEmpty()) {
            System.out.println("\nNo hay clientes registrados.");
            return;
        }
        
        System.out.println("\n=== LISTADO DE CLIENTES ===");
        System.out.printf("%-20s %-12s %-30s %-20s\n", 
                         "Nombre", "Cédula", "Correo", "Dirección");
        for(Cliente cliente : clientes) {
            System.out.printf("%-20s %-12d %-30s %-20s\n",
                            cliente.getNombre(),
                            cliente.getCedula(),
                            cliente.getCorreo(),
                            cliente.getDireccion());
        }
    }
    
    private void buscarCliente() {
        System.out.print("\nIngrese cedula a buscar: ");
        try {
            int cedula = scanner.nextInt();
            scanner.nextLine();
            
            clientes.stream()
                .filter(c -> c.getCedula() == cedula)
                .findFirst()
                .ifPresentOrElse(
                    cliente -> {
                        System.out.println("\nCliente encontrado:");
                        cliente.mostrarDatos();
                    },
                    () -> System.out.println("Cliente no encontrado!")
                );
        } catch(InputMismatchException e) {
            System.out.println("Error: Cedula debe ser numérica!");
            scanner.nextLine();
        }
    }
    
    private void editarCliente() {
        System.out.print("\nIngrese cedula del cliente a editar: ");
        try {
            int cedula = scanner.nextInt();
            scanner.nextLine();
            
            clientes.stream()
                .filter(c -> c.getCedula() == cedula)
                .findFirst()
                .ifPresentOrElse(
                    this::editarDatosCliente,
                    () -> System.out.println("Cliente no encontrado!")
                );
        } catch(InputMismatchException e) {
            System.out.println("Error: Cedula debe ser numérica!");
            scanner.nextLine();
        }
    }
    
    private void editarDatosCliente(Cliente cliente) {
        System.out.println("\nEditando cliente:");
        System.out.println("[Enter para mantener valor actual]");
        
        System.out.print("Nuevo nombre (" + cliente.getNombre() + "): ");
        String nuevoNombre = scanner.nextLine();
        if(!nuevoNombre.isEmpty()) cliente.setNombre(nuevoNombre);
        
        System.out.print("Nuevo correo (" + cliente.getCorreo() + "): ");
        String nuevoCorreo = scanner.nextLine();
        if(!nuevoCorreo.isEmpty()) cliente.setCorreo(nuevoCorreo);
        
        System.out.print("Nueva direccion (" + cliente.getDireccion() + "): ");
        String nuevaDireccion = scanner.nextLine();
        if(!nuevaDireccion.isEmpty()) cliente.setDireccion(nuevaDireccion);
        
        System.out.println("Datos actualizados correctamente!");
    }
    
    private void guardarYSalir() {
        Archivo.guardarClientes(clientes);
        System.out.println("Datos guardados exitosamente en clientes.csv");
        System.out.println("Saliendo del sistema...");
    }
}



