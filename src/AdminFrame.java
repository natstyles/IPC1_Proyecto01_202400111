import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import models.Cliente;
import models.ClientesUtils;
import models.Producto;
import models.ProductoUtils;

public class AdminFrame extends JFrame {
    //LISTA DE CLIENTES
    private List<Cliente> listaClientes;

    //TABLA DE CLIENTES
    private JTable tablaClientes;

    //METODO PARA INICIALIZAR LA TABLA DE CLIENTES
    private void inicializarTablaClientes() {
        String[] columnas = {"Cliente", "NIT", "Compras realizadas"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        tablaClientes.setModel(modelo);

        for (Cliente cliente : listaClientes) {
            modelo.addRow(new Object[]{cliente.getNombre(), cliente.getNit(), cliente.getComprasRealizadas()});
        }
    }

    public AdminFrame() {
        //CARGAR CLIENTES DESDE ARCHIVO
        listaClientes = ClientesUtils.cargarClientesDesdeArchivo("clientes.csv");

        //CARGAR PRODUCTOS DESDE ARCHIVO
        List<Producto> listaProductos = ProductoUtils.cargarProductosDesdeArchivo("productos.csv");

        //CONFIGURACIÓN BÁSICA DE LA VENTANA
        setTitle("Módulo Administrador");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //PANEL SUPERIOR
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //TÍTULO PRINCIPAL
        JLabel lblTitulo = new JLabel("Módulo administrador", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(lblTitulo);

        topPanel.add(Box.createVerticalStrut(10));

        //SUBTÍTULO
        JLabel lblClientes = new JLabel("Clientes registrados en el sistema", JLabel.CENTER);
        lblClientes.setFont(new Font("Arial", Font.PLAIN, 14));
        lblClientes.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(lblClientes);

        add(topPanel, BorderLayout.NORTH);

        //PANEL CENTRAL
        JPanel centralPanel = new JPanel(new BorderLayout());

        //TABLA DE CLIENTES
        String[] columnas = {"Cliente", "NIT", "Compras realizadas"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        tablaClientes = new JTable(modelo);
        JScrollPane tableScrollPane = new JScrollPane(tablaClientes);
        centralPanel.add(tableScrollPane, BorderLayout.CENTER);

        //CARGAR DATOS A LA TABLA DESDE EL ARCHIVO
        inicializarTablaClientes();

        //PANEL DE BOTONES A LA DERECHA
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        JButton btnProductos = new JButton("Módulo de productos");
        JButton btnClientes = new JButton("Módulo de clientes");
        JButton btnVentas = new JButton("Módulo de ventas");

        rightPanel.add(btnProductos);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(btnClientes);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(btnVentas);

        centralPanel.add(rightPanel, BorderLayout.EAST);
        add(centralPanel, BorderLayout.CENTER);

        //PANEL INFERIOR
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        JButton btnGuardar = new JButton("Guardar información");
        JButton btnCerrarSesion = new JButton("Cerrar sesión");

        btnGuardar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCerrarSesion.setAlignmentX(Component.CENTER_ALIGNMENT);

        bottomPanel.add(btnGuardar);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bottomPanel.add(btnCerrarSesion);

        add(bottomPanel, BorderLayout.SOUTH);

        //AÑADIENDO EVENTOS DE BOTONES
        btnProductos.addActionListener(e -> {
            dispose();
            new ProductosFrame();
        });

        btnClientes.addActionListener(e -> {
            new ModuloClientesFrame(listaClientes, tablaClientes);
        });

        btnVentas.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Abriendo módulo de ventas", "Módulo de ventas", JOptionPane.INFORMATION_MESSAGE);
            new ModuloVentasFrame(listaClientes, listaProductos, this); // Pasar referencia
        });

        btnGuardar.addActionListener(e -> {
            //GUARDAR CLIENTES EN EL ARCHIVO CSV
            ClientesUtils.guardarClientesEnArchivo(listaClientes, "clientes.csv");
            JOptionPane.showMessageDialog(null, "Información guardada correctamente", "Guardando", JOptionPane.INFORMATION_MESSAGE);

            //TABLA ACTUALIZADA CON NUEVOS DATOS
            inicializarTablaClientes();
        });

        btnCerrarSesion.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Cerrando sesión", "Cerrando sesión", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new LoginFrame();
        });

        //CENTRAR Y MOSTRAR VENTANA
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //METODO PARA REFRESCAR LA TABLA DESPUES DE UNA ACTUALIZACIÓN
    public void refrescarTablaClientes(){
        DefaultTableModel modelo = (DefaultTableModel) tablaClientes.getModel();
        modelo.setRowCount(0); //LIMPIAMOS TABLA

        //VOLVEMOS A CARGAR LOS DATOS ACTUALIZADOS DESDE listaClientes
        for(Cliente cliente : listaClientes){
            modelo.addRow(new Object[]{cliente.getNombre(), cliente.getNit(), cliente.getComprasRealizadas()});
        }


    }

    public static void main(String[] args) {
        new AdminFrame();
    }
}
