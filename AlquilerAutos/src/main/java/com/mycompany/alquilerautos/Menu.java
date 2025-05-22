package com.mycompany.alquilerautos;

import java.util.*;

public class Menu {

    private Scanner scanner;
    private List<Cliente> clientes;
    private List<Vehiculo> vehiculos;

    public Menu() {
        scanner = new Scanner(System.in);
        clientes = Archivo.cargarClientes();
        vehiculos = Archivo.cargarVehiculos();
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n=== SISTEMA DE ALQUILER DE AUTOS ===");
            System.out.println("1. Registrar Alquiler");
            System.out.println("2. Mostrar todos los clientes");
            System.out.println("3. Buscar cliente por cédula");
            System.out.println("4. Editar cliente");
            System.out.println("5. Modo Administrador");
            System.out.println("6. Guardar y salir");
            System.out.print("Seleccion: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> registrarAlquiler();
                    case 2 -> mostrarClientes();
                    case 3 -> buscarCliente();
                    case 4 -> editarCliente();
                    case 5 -> menuAdministrador();
                    case 6 -> guardarYSalir();
                    default -> System.out.println("Opción no válida!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Ingrese un número válido!");
                scanner.nextLine();
                opcion = 0;
            }

        } while (opcion != 6);
    }

    private void registrarAlquiler() {
        Cliente nuevo = new Cliente("", 0, "", "");
        nuevo.pedirDatos();

        if (cedulaExiste(nuevo.getCedula())) {
            System.out.println("Ya existe un cliente con esa cédula.");
            return;
        }

        clientes.add(nuevo);
        Archivo.guardarClientes(clientes);

        limpiarPantalla();

        vehiculos = Archivo.cargarVehiculos();
        List<Vehiculo> disponibles = vehiculos.stream()
                .filter(v -> v.getEstado().equalsIgnoreCase("Disponible"))
                .toList();

        if (disponibles.isEmpty()) {
            System.out.println("No hay vehículos disponibles en este momento.");
            return;
        }

        System.out.println("=== VEHÍCULOS DISPONIBLES ===");
        for (int i = 0; i < disponibles.size(); i++) {
            System.out.println((i + 1) + ". " + disponibles.get(i));
        }

        System.out.print("Seleccione el número del vehículo a alquilar: ");
        int seleccion = scanner.nextInt();
        scanner.nextLine();

        if (seleccion < 1 || seleccion > disponibles.size()) {
            System.out.println("Selección inválida.");
            return;
        }

        Vehiculo elegido = disponibles.get(seleccion - 1);
        elegido.setEstado("Ocupado");
        Archivo.guardarVehiculos(vehiculos);

        Alquiler nuevoAlquiler = new Alquiler(nuevo, elegido);
        Archivo.guardarAlquiler(nuevoAlquiler);

        System.out.println("Alquiler registrado con éxito.");
    }

    private boolean cedulaExiste(int cedula) {
        return clientes.stream().anyMatch(c -> c.getCedula() == cedula);
    }

    private void mostrarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        System.out.printf("\n%-20s %-10s %-25s %-20s\n", "Nombre", "Cédula", "Correo", "Dirección");
        for (Cliente c : clientes) {
            System.out.printf("%-20s %-10d %-25s %-20s\n",
                    c.getNombre(), c.getCedula(), c.getCorreo(), c.getDireccion());
        }
    }

    private void buscarCliente() {
        System.out.print("Ingrese la cédula: ");
        try {
            int cedula = scanner.nextInt();
            scanner.nextLine();
            clientes.stream()
                    .filter(c -> c.getCedula() == cedula)
                    .findFirst()
                    .ifPresentOrElse(
                            Cliente::mostrarDatos,
                            () -> System.out.println("Cliente no encontrado.")
                    );
        } catch (InputMismatchException e) {
            System.out.println("Cédula inválida.");
            scanner.nextLine();
        }
    }

    private void editarCliente() {
        System.out.print("Cédula del cliente a editar: ");
        try {
            int cedula = scanner.nextInt();
            scanner.nextLine();

            clientes.stream()
                    .filter(c -> c.getCedula() == cedula)
                    .findFirst()
                    .ifPresentOrElse(this::editarDatosCliente,
                            () -> System.out.println("Cliente no encontrado."));
        } catch (InputMismatchException e) {
            System.out.println("Cédula inválida.");
            scanner.nextLine();
        }
    }

    private void editarDatosCliente(Cliente c) {
        System.out.print("Nuevo nombre (" + c.getNombre() + "): ");
        String nombre = scanner.nextLine();
        if (!nombre.isEmpty()) c.setNombre(nombre);

        System.out.print("Nuevo correo (" + c.getCorreo() + "): ");
        String correo = scanner.nextLine();
        if (!correo.isEmpty()) c.setCorreo(correo);

        System.out.print("Nueva dirección (" + c.getDireccion() + "): ");
        String direccion = scanner.nextLine();
        if (!direccion.isEmpty()) c.setDireccion(direccion);

        System.out.println("Cliente actualizado.");
    }

    private void guardarYSalir() {
        Archivo.guardarClientes(clientes);
        Archivo.guardarVehiculos(vehiculos);
        System.out.println("Datos guardados. Saliendo...");
    }

    private void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void menuAdministrador() {
        System.out.print("Ingrese la contraseña de administrador: ");
        String clave = scanner.nextLine();
        if (!clave.equals("admin123")) {
            System.out.println("Clave incorrecta.");
            return;
        }

        int opcion;
        do {
            System.out.println("\n=== ADMINISTRADOR ===");
            System.out.println("1. Registrar vehículo");
            System.out.println("2. Editar vehículo");
            System.out.println("3. Editar cliente");
            System.out.println("4. Eliminar cliente");
            System.out.println("5. Eliminar vehículo");
            System.out.println("6. Mostrar clientes");
            System.out.println("7. Mostrar vehículos");
            System.out.println("8. Ver historial de alquileres");
            System.out.println("9. Volver al menú principal");
            System.out.print("Opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> registrarVehiculo();
                case 2 -> editarVehiculo();
                case 3 -> editarCliente();
                case 4 -> eliminarCliente();
                case 5 -> eliminarVehiculo();
                case 6 -> mostrarClientes();
                case 7 -> mostrarVehiculos();
                case 8 -> Archivo.mostrarHistorialAlquileres();
            }
        } while (opcion != 9);
    }

    private void registrarVehiculo() {
        System.out.print("Marca: ");
        String marca = scanner.nextLine();
        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();
        System.out.print("Nombre identificador: ");
        String nombre = scanner.nextLine();
        System.out.print("Valor por hora: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();
        vehiculos.add(new Vehiculo(marca, modelo, nombre, valor, "Disponible"));
        Archivo.guardarVehiculos(vehiculos);
        System.out.println("Vehículo registrado.");
    }

    private void editarVehiculo() {
        mostrarVehiculos();
        System.out.print("Seleccione número de vehículo a editar: ");
        int idx = scanner.nextInt() - 1;
        scanner.nextLine();
        if (idx < 0 || idx >= vehiculos.size()) return;
        Vehiculo v = vehiculos.get(idx);

        System.out.print("Nueva marca (" + v.getMarca() + "): ");
        String marca = scanner.nextLine();
        if (!marca.isEmpty()) v = new Vehiculo(marca, v.getModelo(), v.getNombre(), v.getValorHora(), v.getEstado());

        System.out.print("Nuevo modelo (" + v.getModelo() + "): ");
        String modelo = scanner.nextLine();
        if (!modelo.isEmpty()) v = new Vehiculo(v.getMarca(), modelo, v.getNombre(), v.getValorHora(), v.getEstado());

        System.out.print("Nuevo valor/hora (" + v.getValorHora() + "): ");
        String valStr = scanner.nextLine();
        if (!valStr.isEmpty()) {
            double valor = Double.parseDouble(valStr);
            v = new Vehiculo(v.getMarca(), v.getModelo(), v.getNombre(), valor, v.getEstado());
        }

        vehiculos.set(idx, v);
        Archivo.guardarVehiculos(vehiculos);
        System.out.println("Vehículo actualizado.");
    }

    private void eliminarCliente() {
        System.out.print("Ingrese cédula a eliminar: ");
        int cedula = scanner.nextInt();
        scanner.nextLine();
        clientes.removeIf(c -> c.getCedula() == cedula);
        Archivo.guardarClientes(clientes);
        System.out.println("Cliente eliminado.");
    }

    private void eliminarVehiculo() {
        mostrarVehiculos();
        System.out.print("Seleccione número de vehículo a eliminar: ");
        int idx = scanner.nextInt() - 1;
        scanner.nextLine();
        if (idx >= 0 && idx < vehiculos.size()) {
            vehiculos.remove(idx);
            Archivo.guardarVehiculos(vehiculos);
            System.out.println("Vehículo eliminado.");
        }
    }

    private void mostrarVehiculos() {
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos registrados.");
            return;
        }

        for (int i = 0; i < vehiculos.size(); i++) {
            System.out.println((i + 1) + ". " + vehiculos.get(i));
        }
    }
}