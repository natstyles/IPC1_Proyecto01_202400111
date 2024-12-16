import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminFrame extends JFrame {

    public AdminFrame() {
        // Configuración básica de la ventana
        setTitle("Módulo Administrador");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior: título y subtítulo
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS)); // Permite organizar elementos verticalmente
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Añade un margen

        // Título principal
        JLabel lblTitulo = new JLabel("Módulo administrador", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(lblTitulo);

        topPanel.add(Box.createVerticalStrut(10)); // Espacio vertical (10 píxeles)

        // Subtítulo
        JLabel lblClientes = new JLabel("Clientes registrados en el sistema", JLabel.CENTER);
        lblClientes.setFont(new Font("Arial", Font.PLAIN, 14)); // Fuente más pequeña
        lblClientes.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar horizontalmente
        topPanel.add(lblClientes);

        // Añadir el panel superior al norte del BorderLayout
        add(topPanel, BorderLayout.NORTH);

        // Panel central
        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new BorderLayout());


        String[] columnNames = {"Cliente", "NIT", "Compras realizadas"};
        Object[][] data = {
                {"ola si", "10005", 0},
                {"Consumidor Final", "C/F", 0}
        };
        JTable table = new JTable(new DefaultTableModel(data, columnNames));
        JScrollPane tableScrollPane = new JScrollPane(table);
        centralPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Panel de botones a la derecha
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        JButton btnProductos = new JButton("Módulo de productos");
        JButton btnClientes = new JButton("Módulo de clientes");
        JButton btnVentas = new JButton("Módulo de ventas");

        rightPanel.add(btnProductos);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre botones
        rightPanel.add(btnClientes);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre botones
        rightPanel.add(btnVentas);

        centralPanel.add(rightPanel, BorderLayout.EAST);

        add(centralPanel, BorderLayout.CENTER);

        // panel inveferior
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        JButton btnGuardar = new JButton("Guardar información");
        JButton btnCerrarSesion = new JButton("Cerrar sesión");

        btnGuardar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCerrarSesion.setAlignmentX(Component.CENTER_ALIGNMENT);

        bottomPanel.add(btnGuardar);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre botones
        bottomPanel.add(btnCerrarSesion);

        add(bottomPanel, BorderLayout.SOUTH);

        //AÑADIENDO EVENTOS DE BOTONES
        btnProductos.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Abriendo módulo de productos", "Módulo de productos", JOptionPane.INFORMATION_MESSAGE);
            new ProductosFrame();
        });

        btnClientes.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Abriendo módulo de clientes", "Módulo de clientes", JOptionPane.INFORMATION_MESSAGE);
        });

        btnVentas.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Abriendo módulo de ventas", "Módulo de ventas", JOptionPane.INFORMATION_MESSAGE);
        });

        btnGuardar.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Guardando información", "Guardando", JOptionPane.INFORMATION_MESSAGE);
        });

        btnCerrarSesion.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Cerrando sesión", "Cerrando sesión", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });

        //CENTRAR Y MOSTRAR VENTANA
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new AdminFrame();
    }


}