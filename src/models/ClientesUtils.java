package models;

//LIBRERIAS
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class ClientesUtils {
    // MÉTODO PARA GUARDAR CLIENTES EN UN ARCHIVO CSV
    public static void guardarClientesEnArchivo(List<Cliente> listaClientes, String archivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            // Escribir encabezados siempre
            writer.println("nombre_cliente;nit_cliente;compras_realizadas");

            // Escribir la lista de clientes
            for (Cliente cliente : listaClientes) {
                writer.println(cliente.getNombre() + ";" + cliente.getNit() + ";" + cliente.getComprasRealizadas());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar clientes: " + e.getMessage());
        }
    }

    // MÉTODO PARA CARGAR CLIENTES DESDE UN ARCHIVO CSV
    public static List<Cliente> cargarClientesDesdeArchivo(String archivo) {
        List<Cliente> listaClientes = new ArrayList<>();
        File file = new File(archivo);

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String linea;
                boolean primeraLinea = true; // Bandera para identificar la primera línea (encabezado)

                while ((linea = reader.readLine()) != null) {
                    if (primeraLinea) {
                        primeraLinea = false; // Omitir el encabezado
                        continue;
                    }

                    String[] partes = linea.split(";");
                    if (partes.length == 3) { // Verificar que la línea tenga 3 partes
                        listaClientes.add(new Cliente(partes[0], partes[1], Integer.parseInt(partes[2])));
                    }
                }
            } catch (IOException e) {
                System.out.println("Error al leer el archivo: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Error en el formato del archivo: " + e.getMessage());
            }
        }
        return listaClientes;
    }


}
