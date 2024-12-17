//LIBRERIAS
package models;
import java.io.*;
import java.util.ArrayList;

public class Producto {
    private String nombre;
    private double precio;
    private int stock;
    private int ventas;

    //Constructor
    public Producto(String nombre, double precio, int stock, int ventas) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.ventas = ventas;
    }

    //Getters y Setters
    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public double getPrecio(){
        return precio;
    }

    public void setPrecio(double precio){
        this.precio = precio;
    }

    public int getStock(){
        return stock;
    }

    public void setStock(int stock){
        this.stock = stock;
    }

    public int getVentas(){
        return ventas;
    }

    public void setVentas(int ventas){
        this.ventas = ventas;
    }

    public void incrementarVentas(int cantidad){
        ventas += cantidad;
    }

    @Override
    public String toString(){
        return String.format("%s;%.2f;%d", nombre, precio, stock);
    }
}