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

    //METODO PARA GENERAR REPORTE DE TOP 5 PRODUCTOS MAS VENDIDOS
    public static void generarReporteHTML(List<Producto> listaProductos, String rutaArchivo){
        try{
            //ORDENAMIENTO BURBUJA
            for(int i = 0; i < listaProductos.size() -1; i++){
                for(int j = 0; j < listaProductos.size() - i - 1; j++){
                    //if(listaProductos.get(j).getVentas() < listaProductos.get(j + 1).getVentas()){
                        //INTERCAMBIAR PRODUCTOS
                        //Producto temp = listaProductos.get(j);
                        //listaProductos.set(j, listaProductos.get(j + 1));
                        //listaProductos.set(j + 1, temp);
                    //}
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
                    //writer.printf("<tr><td>%s</td><td>Q%.2f</td><td>%d</td><td>%d</td></tr>%n",
                            //p.getNombre(), p.getPrecio(), p.getStock(), p.getVentas());
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
