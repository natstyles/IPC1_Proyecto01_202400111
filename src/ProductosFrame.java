//LIBRERIAS
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import models.Producto;
import models.ProductoUtils;

public class ProductosFrame extends JFrame{
    private List<Producto> listaProductos;
    private JTable tablaProductos;

    public ProductosFrame(){
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
        mainPanel.add(lblTitulo, BorderLayout.NORTH);;


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
        JButton btnRegresar = new JButton("Volver al módulo de administración");
        btnRegresar.addActionListener(e -> {
            dispose();
            new AdminFrame(); // Regresar al módulo de administrador
        });

        mainPanel.add(btnRegresar, BorderLayout.SOUTH);

        add(mainPanel);

        //CARGAR PRODUCTOS DESDE ARCHIVO
        listaProductos = ProductoUtils.cargarProductosDesdeArchivo("productos.csv");

        //LLENAR LA TABLA CON LOS DATOS CARGADOS
        inicializarTabla();

        //ACCION DE BOTON DE CREAR PRODUCTO
        btnCrearProducto.addActionListener(e -> {
            new crearProductosFrame(listaProductos, tablaProductos);
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    //METODO PARA INICIALIZAR LA TABLA CON LOS DATOS CARGADOS
    private void inicializarTabla(){
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
}