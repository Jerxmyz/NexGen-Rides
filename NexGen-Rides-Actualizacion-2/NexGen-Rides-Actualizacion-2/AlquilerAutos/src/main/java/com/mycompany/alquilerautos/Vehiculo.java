
package com.mycompany.alquilerautos;

public class Vehiculo {
    private String marca;
    private String modelo;
    private String nombre;
    private double valorHora;
    private String estado;

    public Vehiculo(String marca, String modelo, String nombre, double valorHora, String estado) {
        this.marca = marca;
        this.modelo = modelo;
        this.nombre = nombre;
        this.valorHora = valorHora;
        this.estado = estado;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getValorHora() {
        return valorHora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String toCSV(){
        return String.join(",", marca, modelo, nombre, String.valueOf(valorHora), estado);
    }
    public String toString(){
        return nombre + " | " + marca + " | " + modelo + " | $" + valorHora + "/hora | " + estado;
    }
    
}
