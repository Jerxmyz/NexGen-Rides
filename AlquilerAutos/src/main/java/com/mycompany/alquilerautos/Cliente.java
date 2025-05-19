package com.mycompany.alquilerautos;
import java.util.Scanner;
public class Cliente {
    private String nombre;
    private int cedula;
    private String correo;
    private String direccion;
    private String ciudad;    
    
    
    public Cliente(String nombre,int cedula,String correo,String direccion){
        this.nombre = nombre;
        this.cedula = cedula;
        this.correo = correo;
        this.direccion = direccion;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public int getCedula(){
        return cedula;
    }
    
    public String getCorreo(){
        return correo;
    }
    
    public String getDireccion(){
        return direccion;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public void setCedula(int cedula){
        this.cedula = cedula;
    }
    
    public void setCorreo(String correo){
        this.correo = correo;
    }
    
    public void setDireccion(String direccion){
        this.direccion = direccion;
    }
    
    public void pedirDatos(){
        Scanner enter = new Scanner(System.in);
        System.out.println("Ingrese sus nombres: ");
         setNombre(enter.nextLine());
        System.out.println("Ingrese su cedula: ");
        setCedula(enter.nextInt());
        enter.nextLine();
        System.out.println("Ingrese su direccion de correo: ");
        setCorreo(enter.nextLine());
        System.out.println("Ingrese su direccion de residencia:");
        setDireccion(enter.nextLine());
        
    }
    
    public void mostrarDatos(){
        System.out.println("Nombres: "+ getNombre());
        System.out.println("Cedula: "+ getCedula());
        System.out.println("Correo: "+ getCorreo());
        System.out.println("Direccion: "+ getDireccion());
    }
}
