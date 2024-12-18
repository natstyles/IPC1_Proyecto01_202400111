// LIBRERIAS
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import models.Cliente;
import models.Producto;
import models.ProductoUtils;
import models.Cliente;
import models.ClientesUtils;

public class RealizarVentaFrame extends JFrame {
    private List<Cliente> listaClientes;
    private List<Producto> listaProductos;
    private AdminFrame adminFrame;

    public RealizarVentaFrame(List<Cliente> listaClientes, List<Producto> listaProductos, AdminFrame adminFrame) {
        this.listaClientes = listaClientes;
        this.listaProductos = listaProductos;
        this.adminFrame = adminFrame;

        // CONFIGURAMOS LA VENTANA
        setTitle("Realizar Venta");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // PANEL PRINCIPAL
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // TITULO
        JLabel lblTitulo = new JLabel("Realizar Venta", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblTitulo);
        mainPanel.add(Box.createVerticalStrut(20));

        // COMBOBOX DE CLIENTES
        JPanel clientePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblCliente = new JLabel("Cliente:");
        JComboBox<String> comboClientes = new JComboBox<>();
        for (Cliente cliente : listaClientes) {
            comboClientes.addItem(cliente.getNombre());
        }
        clientePanel.add(lblCliente);
        clientePanel.add(comboClientes);
        mainPanel.add(clientePanel);

        // SELECCIÓN DE PRODUCTOS
        JPanel productoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblProducto = new JLabel("Producto:");
        JComboBox<String> comboProductos = new JComboBox<>();
        for (Producto producto : listaProductos) {
            comboProductos.addItem(producto.getNombre());
        }
        productoPanel.add(lblProducto);
        productoPanel.add(comboProductos);
        mainPanel.add(productoPanel);

        // CAMPO PARA CANTIDAD
        JPanel cantidadPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblCantidad = new JLabel("Cantidad:");
        JTextField txtCantidad = new JTextField(10);
        cantidadPanel.add(lblCantidad);
        cantidadPanel.add(txtCantidad);
        mainPanel.add(cantidadPanel);

        // BOTON DE VENDER
        JButton btnVender = new JButton("Vender");
        btnVender.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(btnVender);

        // MENSAJE DE ERROR
        JLabel lblError = new JLabel("", JLabel.CENTER);
        lblError.setForeground(Color.RED);
        lblError.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblError);

        // EVENTO DEL BOTON VENDER
        btnVender.addActionListener(e -> {
            String clienteSeleccionado = (String) comboClientes.getSelectedItem();
            String productoSeleccionado = (String) comboProductos.getSelectedItem();
            String cantidadTexto = txtCantidad.getText().trim();

            if (cantidadTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La cantidad es requerida.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int cantidad;
            try {
                cantidad = Integer.parseInt(cantidadTexto);
                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Buscar el producto y validar el stock
            Producto productoVender = null;
            for (Producto producto : listaProductos) {
                if (producto.getNombre().equals(productoSeleccionado)) {
                    productoVender = producto;
                    break;
                }
            }

            if (productoVender != null) {
                if (productoVender.getStock() >= cantidad) {
                    // Restar stock y actualizar ventas
                    productoVender.setStock(productoVender.getStock() - cantidad);
                    productoVender.incrementarVentas(cantidad);

                    // Actualizar compras realizadas del cliente
                    for (Cliente cliente : listaClientes) {
                        if (cliente.getNombre().equals(clienteSeleccionado)) {
                            cliente.incrementarCompras(cantidad);
                            break;
                        }
                    }

                    // Guardar productos y clientes en los archivos
                    ProductoUtils.guardarProductosDesdeArchivo(listaProductos, "productos.csv");
                    ClientesUtils.guardarClientesEnArchivo(listaClientes, "clientes.csv");

                    // Refrescar tabla de AdminFrame
                    adminFrame.refrescarTablaClientes();

                    // Mostrar factura
                    String factura = "Factura:\n" +
                            "Cliente: " + clienteSeleccionado + "\n" +
                            "Producto: " + productoSeleccionado + "\n" +
                            "Cantidad: " + cantidad + "\n" +
                            "Total: Q" + (productoVender.getPrecio() * cantidad) + "\n" +
                            "Gracias por su compra!";
                    JOptionPane.showMessageDialog(this, factura, "Venta Realizada", JOptionPane.INFORMATION_MESSAGE);

                    //ACTUALIZAMOS LA TABLA EN ADMINFRAME
                    adminFrame.refrescarTablaClientes();

                    dispose(); // Cerrar ventana
                } else {
                    JOptionPane.showMessageDialog(this, "Stock insuficiente. Disponible: " + productoVender.getStock(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });



        // AÑADIR PANEL PRINCIPAL A LA VENTANA
        add(mainPanel, BorderLayout.CENTER);

        // CENTRAR Y MOSTRAR VENTANA
        setLocationRelativeTo(null);
        setVisible(true);
    }


}
