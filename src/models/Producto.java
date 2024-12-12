//LIBRERIAS
package models;
import java.io.*;
import java.util.ArrayList;

public class Producto {
    private String nombre;
    private double precio;
    private int stock;

    //Constructor
    public Producto(String nombre, double precio, int stock){
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
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

    @Override
    public String toString(){
        return String.format("%s;%.2f;%d", nombre, precio, stock);
    }
}