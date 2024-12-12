//LIBRERIAS
package models;
import  java.io.*;
import  java.util.ArrayList;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class ProductoUtils {

    //METODO PARA GUARDAR PRODUCTOS EN UN ARCHIVO CSV

    public static void guardarProductosDesdeArchivo(List<Producto> productos, String rutaArchivo) {
        try (PrintWriter writer = new PrintWriter(new File(rutaArchivo))) {
            writer.println("nombre;precio;stock");
            for (Producto producto : productos) {
                writer.printf("%s;%.2f;%d%n", producto.getNombre(), producto.getPrecio(), producto.getStock());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar productos: " + e.getMessage());
        }
    }

    //METODO PARA CARGAR PRODUCTOS DESDE UN ARCHIVO CSV

    public static List<Producto> cargarProductosDesdeArchivo(String rutaArchivo){
        List<Producto> productos = new ArrayList<>();

        File archivo = new File(rutaArchivo);

        if(!archivo.exists()){
            System.out.println("El archivo no existe: "+ rutaArchivo);
            return productos;
        }

        try(BufferedReader br = new BufferedReader(new FileReader(archivo))){
            String linea;

            //TAG PARA IDENTIFICAR LA PRIMERA LINEA
            boolean primeraLinea = true;

            while ((linea = br.readLine()) != null){

                if(primeraLinea){
                    primeraLinea = false;
                    continue;
                }

                String[] partes = linea.split(";");

                if(partes.length == 3){
                    //ASEGURARSE QUE TIENE 3 COLUMNAS
                    try{
                        String nombre = partes[0].trim();
                        double precio = Double.parseDouble(partes[1].trim());
                        int stock = Integer.parseInt(partes[2].trim());

                        //Crear y añadir productos a a lista
                        Producto producto = new Producto(nombre, precio, stock);
                        productos.add(producto);
                    } catch (RuntimeException e) {
                        System.out.println("Error en formato numérico en la linea: " + linea);
                    }
                }else{
                    System.out.println("Formato inválido en la linea: " + linea);
                }
            }
        }catch (IOException e){
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

        return productos;
    }
}
