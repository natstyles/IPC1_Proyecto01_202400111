//LIBRERIAS
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import models.Producto;
import models.ProductoUtils;
import javax.swing.JFileChooser;
import java.io.FileReader;
import java.io.IOException;

public class ProductosFrame extends JFrame {
    private List<Producto> listaProductos;
    private JTable tablaProductos;

    public ProductosFrame() {
        //PANEL PRINCIPAL
        JPanel mainPanel = new JPanel(new BorderLayout());

        //VENTANA
        setTitle("Módulo de productos");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //PANEL SUPERIOR
        JLabel lblTitulo = new JLabel("Módulo de productos", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

        //TABLA DE PRODUCTOS
        String[] columnas = {"Producto", "Precio", "Stock"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        tablaProductos = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        //BOTONES DE ACCIONES
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(7, 1, 5, 5));

        JButton btnCrearProducto = new JButton("Creación de producto");
        JButton btnCargaMasiva = new JButton("Carga masiva de productos");
        JButton btnEditarProducto = new JButton("Edición de productos");
        JButton btnEliminarProducto = new JButton("Eliminación de productos");
        JButton btnReporteProductos = new JButton("Reporte de productos más vendidos");

        buttonPanel.add(btnCrearProducto);
        buttonPanel.add(btnCargaMasiva);
        buttonPanel.add(btnEditarProducto);
        buttonPanel.add(btnEliminarProducto);
        buttonPanel.add(btnReporteProductos);

        mainPanel.add(buttonPanel, BorderLayout.EAST);

        //PANEL INFERIOR
        //BOTON DE REGRESO (DISPOSE Y ABRIR DE NUEVO EL ADMINFRAME)
        JButton btnRegresar = new JButton("Volver al módulo de administración");
        btnRegresar.addActionListener(e -> {
            dispose();
            new AdminFrame();
        });
        mainPanel.add(btnRegresar, BorderLayout.SOUTH);

        add(mainPanel);

        //CARGAR PRODUCTOS DESDE ARCHIVO
        listaProductos = ProductoUtils.cargarProductosDesdeArchivo("productos.csv");

        //LLENAR LA TABLA CON LOS DATOS CARGADOS
        inicializarTabla();

        //ACCION DE BOTON DE CREAR PRODUCTO
        btnCrearProducto.addActionListener(e -> new crearProductosFrame(listaProductos, tablaProductos));

        //ACCIÓN DE BOTON DE CARGA MASIVA
        btnCargaMasiva.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int seleccion = fileChooser.showOpenDialog(this);

            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File archivo = fileChooser.getSelectedFile();
                cargarProductosDesdeArchivo(archivo);
            }
        });

        //ACCIÓN DE BOTON DE EDITAR PRODUCTO
        btnEditarProducto.addActionListener(e -> {
            if (listaProductos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay productos disponibles para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            } else {
                new EditarProductoFrame(listaProductos, tablaProductos);
            }
        });

        //ACCIÓN DE BOTON DE ELIMINAR PRODUCTO
        btnEliminarProducto.addActionListener(e -> new EliminarProductosFrame(listaProductos, tablaProductos));

        //ACCIÓN DE BOTON DE REPORTE DE PRODUCTOS MÁS VENDIDOS
        btnReporteProductos.addActionListener(e -> {
            String rutaArchivo = "reporte_top5Productos.html";
            ProductoUtils.generarReporteHTML(listaProductos, rutaArchivo);
            JOptionPane.showMessageDialog(this, "Reporte generado en: " + rutaArchivo, "Reporte generado", JOptionPane.INFORMATION_MESSAGE);

            try {
                Desktop.getDesktop().browse(new java.io.File(rutaArchivo).toURI());
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "No se pudo abrir el reporte.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    //METODO PARA INICIALIZAR LA TABLA CON LOS DATOS CARGADOS
    private void inicializarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tablaProductos.getModel();
        modelo.setRowCount(0); // Limpiar la tabla

        //Agregar los productos al modelo
        for (Producto producto : listaProductos) {
            Object[] fila = {producto.getNombre(), producto.getPrecio(), producto.getStock()};
            modelo.addRow(fila);
        }
    }

    public static void main(String[] args) {
        new ProductosFrame();
    }

    //METODO PARA CARGAR PRODUCTOS DESDE ARCHIVO
    private void cargarProductosDesdeArchivo(File archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;

            boolean primeraLinea = true; //Omitir primera linea (encabezado)

            int productosAgregados = 0;
            int productosIgnorados = 0;

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                String[] partes = linea.split(";");
                if (partes.length == 4) {
                    String nombre = partes[0].trim();
                    String precioTexto = partes[1].trim();
                    String stockTexto = partes[2].trim();
                    String ventasTexto = partes[3].trim();

                    try {
                        double precio = Double.parseDouble(precioTexto);
                        int stock = Integer.parseInt(stockTexto);
                        int ventas = Integer.parseInt(ventasTexto);

                        //VALIDACIONES
                        if (precio <= 0 || stock < 0 || ventas < 0 || productoExiste(nombre)) {
                            productosIgnorados++;
                            continue;
                        }

                        //CREAR Y AGREGAR PRODUCTO
                        Producto producto = new Producto(nombre, precio, stock, ventas);
                        listaProductos.add(producto);
                        productosAgregados++;
                    } catch (NumberFormatException e) {
                        productosIgnorados++;
                    }
                } else {
                    productosIgnorados++;
                }
            }
            //MOSTRAR QUE SE CARGARÁ
            JOptionPane.showMessageDialog(this,
                    "Productos agregados: " + productosAgregados + "\n" +
                            "Productos ignorados: " + productosIgnorados,
                    "Carga Masiva Completada",
                    JOptionPane.INFORMATION_MESSAGE);

            //ACTUALIZAR LA TABLA
            inicializarTabla();

            //GUARDAR PRODUCTOS EN EL ARCHIVO DESPUES DE LA CARGA
            ProductoUtils.guardarProductosDesdeArchivo(listaProductos, "productos.csv");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error al leer el archivo: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    //METODO PARA VERIFICAR SI UN PRODUCTO YA EXISTE
    private boolean productoExiste(String nombre) {
        for (Producto producto : listaProductos) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }
}
