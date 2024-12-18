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
            //Escribir encabezado
            writer.println("nombre;precio;stock;ventas");

            //Escribir cada producto con el campo ventas incluido
            for (Producto producto : productos) {
                writer.println(producto.getNombre() + ";" + producto.getPrecio() + ";" +
                        producto.getStock() + ";" + producto.getVentas());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar productos: " + e.getMessage());
        }
    }

    //METODO PARA CARGAR PRODUCTOS DESDE UN ARCHIVO CSV
    public static List<Producto> cargarProductosDesdeArchivo(String rutaArchivo) {
        List<Producto> productos = new ArrayList<>();
        File archivo = new File(rutaArchivo);

        if (!archivo.exists()) {
            System.out.println("El archivo no existe: " + rutaArchivo);
            return productos;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;

            //TAG PARA IDENTIFICAR LA PRIMERA LÍNEA
            boolean primeraLinea = true;

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false; //Saltar encabezado
                    continue;
                }

                String[] partes = linea.split(";");

                if (partes.length == 4) { //Asegurarse de que la línea tenga todos los campos
                    try {
                        String nombre = partes[0].trim();
                        double precio = Double.parseDouble(partes[1].trim());
                        int stock = Integer.parseInt(partes[2].trim());
                        int ventas = Integer.parseInt(partes[3].trim());

                        //Crear y añadir el producto con el contador de ventas
                        Producto producto = new Producto(nombre, precio, stock, ventas);
                        producto.incrementarVentas(ventas); //Asignar las ventas existentes
                        productos.add(producto);
                    } catch (NumberFormatException e) {
                        System.out.println("Error en formato numérico en la línea: " + linea);
                    }
                } else {
                    System.out.println("Formato inválido en la línea: " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

        return productos;
    }


    //METODO PARA GENERAR REPORTE DE TOP 5 PRODUCTOS MAS VENDIDOS
    public static void generarReporteHTML(List<Producto> listaProductos, String rutaArchivo){
        try{
            //ORDENAMIENTO BURBUJA
            for(int i = 0; i < listaProductos.size() -1; i++){
                for(int j = 0; j < listaProductos.size() - i - 1; j++){
                    if(listaProductos.get(j).getVentas() < listaProductos.get(j + 1).getVentas()){
                        //INTERCAMBIAR PRODUCTOS
                        Producto temp = listaProductos.get(j);
                        listaProductos.set(j, listaProductos.get(j + 1));
                        listaProductos.set(j + 1, temp);
                    }
                }
            }

            //CREAR ARCHIVO HTML
            try(PrintWriter writer = new PrintWriter(rutaArchivo)){
                writer.println("<!DOCTYPE html>");
                writer.println("<html lang='es'>");
                writer.println("<head>");
                writer.println("<meta charset='UTF-8'>");
                writer.println("<title>Reporte de Productos Más Vendidos</title>");
                writer.println("<style>");
                writer.println("table {width: 50%; margin: 20px auto; border-collapse: collapse;}");
                writer.println("th, td {border: 1px solid #ddd; padding: 8px; text-align: center;}");
                writer.println("th {background-color: #f2f2f2;}");
                writer.println("h1 {text-align: center;}");
                writer.println("</style>");
                writer.println("</head>");
                writer.println("<body>");
                writer.println("<h1>Reporte de Productos Más Vendidos</h1>");
                writer.println("<table>");
                writer.println("<tr><th>Producto</th><th>Precio</th><th>Stock</th><th>Ventas</th></tr>");

                //MOSTRAR LOS 5 PRODUCTOS MAS VENDIDOS
                int limite = Math.min(5, listaProductos.size());
                for(int i = 0; i < limite; i++){
                    Producto p = listaProductos.get(i);
                    writer.printf("<tr><td>%s</td><td>Q%.2f</td><td>%d</td><td>%d</td></tr>%n",
                            p.getNombre(), p.getPrecio(), p.getStock(), p.getVentas());
                }

                writer.println("</table>");
                writer.println("</body>");
                writer.println("</html>");

            }
            System.out.println("Reporte generado con éxito en: " + rutaArchivo);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al generar el reporte: " + e.getMessage());
        }
    }
}
