package models;

public class Cliente {
    private String nombre;
    private String nit;
    private int comprasRealizadas;

    public Cliente(String nombre, String nit, int comprasRealizadas) {
        this.nombre = nombre;
        this.nit = nit;
        this.comprasRealizadas = comprasRealizadas;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public int getComprasRealizadas() {
        return comprasRealizadas;
    }

    public void setComprasRealizadas(int compras) {
        this.comprasRealizadas = compras;
    }
}