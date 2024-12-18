// LIBRERIAS
import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import models.Cliente;
import models.Producto;
import models.ProductoUtils;
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
        setLayout(new GridBagLayout()); // NUEVO LAYOUT CENTRADO

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaciado entre componentes
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // TITULO
        JLabel lblTitulo = new JLabel("Realizar Venta", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridwidth = 2; // El título ocupa 2 columnas
        add(lblTitulo, gbc);

        // REINICIAR GRIDBAG PARA SIGUIENTES COMPONENTES
        gbc.gridwidth = 1;
        gbc.gridy++;

        // COMBOBOX DE CLIENTES
        JLabel lblCliente = new JLabel("Cliente:");
        add(lblCliente, gbc);
        gbc.gridx = 1;
        JComboBox<String> comboClientes = new JComboBox<>();
        for (Cliente cliente : listaClientes) {
            comboClientes.addItem(cliente.getNombre());
        }
        add(comboClientes, gbc);

        // REINICIAR PARA SIGUIENTE FILA
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblProducto = new JLabel("Producto:");
        add(lblProducto, gbc);
        gbc.gridx = 1;
        JComboBox<String> comboProductos = new JComboBox<>();
        for (Producto producto : listaProductos) {
            comboProductos.addItem(producto.getNombre());
        }
        add(comboProductos, gbc);

        // CAMPO DE CANTIDAD
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblCantidad = new JLabel("Cantidad:");
        add(lblCantidad, gbc);
        gbc.gridx = 1;
        JTextField txtCantidad = new JTextField(10);
        add(txtCantidad, gbc);

        // BOTON DE VENDER
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton btnVender = new JButton("Vender");
        add(btnVender, gbc);

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

                    // REGISTRAR LA VENTA EN EL ARCHIVO CSV
                    try (PrintWriter writer = new PrintWriter(new FileWriter("ventas.csv", true))) {
                        String fechaVenta = java.time.LocalDate.now().toString();
                        double totalVenta = productoVender.getPrecio() * cantidad;

                        String nitCliente = "";
                        for (Cliente cliente : listaClientes) {
                            if (cliente.getNombre().equals(clienteSeleccionado)) {
                                nitCliente = cliente.getNit();
                                break;
                            }
                        }

                        writer.printf("%s;%s;%s;%d;%.2f;%.2f;%s%n",
                                clienteSeleccionado, nitCliente, productoSeleccionado,
                                cantidad, productoVender.getPrecio(), totalVenta, fechaVenta);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Error al registrar la venta.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    // Guardar cambios
                    ProductoUtils.guardarProductosDesdeArchivo(listaProductos, "productos.csv");
                    ClientesUtils.guardarClientesEnArchivo(listaClientes, "clientes.csv");

                    adminFrame.refrescarTablaClientes();

                    JOptionPane.showMessageDialog(this,
                            "Factura:\nCliente: " + clienteSeleccionado +
                                    "\nProducto: " + productoSeleccionado +
                                    "\nCantidad: " + cantidad +
                                    "\nTotal: Q" + (productoVender.getPrecio() * cantidad),
                            "Venta Realizada", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Stock insuficiente.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // CENTRAR Y MOSTRAR VENTANA
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
