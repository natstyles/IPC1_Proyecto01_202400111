//LIBRERIAS
package models;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class VentasUtils {
    public static void generarReporteVentasHTML(String rutaVentasCSV, String rutaReporteHTML){
        List<String[]> ventas = new ArrayList<>();

        //LEER ARCHIVO DE VENTAS
        try (BufferedReader br = new BufferedReader(new FileReader(rutaVentasCSV))) {
            String linea;
            boolean primeraLinea = true;

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) { // Saltar encabezado
                    primeraLinea = false;
                    continue;
                }
                ventas.add(linea.split(";"));
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de ventas: " + e.getMessage());
            return;
        }

        //GENERAR REPORTE EN HTML
        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaReporteHTML))) {
            writer.println("<!DOCTYPE html>");
            writer.println("<html lang='es'>");
            writer.println("<head>");
            writer.println("<meta charset='UTF-8'>");
            writer.println("<title>Reporte Histórico de Ventas</title>");
            writer.println("<link rel='stylesheet' href='styles/style.css'>");
            writer.println("</head>");
            writer.println("<body>");
            writer.println("<h1>Reporte Histórico de Ventas</h1>");
            writer.println("<table>");
            writer.println("<tr><th>Cliente</th><th>NIT</th><th>Producto</th><th>Cantidad</th><th>Precio Unitario</th><th>Total</th><th>Fecha</th></tr>");

            // ESCRIBIR CADA VENTA EN UNA FILA
            for (String[] venta : ventas) {
                writer.printf("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>Q%.2f</td><td>Q%.2f</td><td>%s</td></tr>%n",
                        venta[0],                // Cliente
                        venta[1],                // NIT
                        venta[2],                // Producto
                        venta[3],                // Cantidad
                        Double.parseDouble(venta[4]), // Precio unitario
                        Double.parseDouble(venta[5]), // Total
                        venta[6]);               // Fecha
            }

            writer.println("</table>");
            writer.println("</body>");
            writer.println("</html>");
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo de reporte: " + e.getMessage());
        }

        System.out.println("Reporte generado en: " + rutaReporteHTML);
    }
}
