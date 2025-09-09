
package com.mycompany.alquilerautos;

public class Alquiler {
    private Cliente cliente;
    private Vehiculo vehiculo;

    public Alquiler(Cliente cliente, Vehiculo vehiculo) {
        this.cliente = cliente;
        this.vehiculo = vehiculo;
    }
    public String toCSV(){
        return String.join(",",cliente.getNombre(), String.valueOf(cliente.getCedula()), cliente.getCorreo(), cliente.getDireccion(), 
                vehiculo.getNombre(), vehiculo.getMarca(), vehiculo.getModelo(), String.valueOf(vehiculo.getValorHora()));
    }
    
    
}
