// LIBRERIAS
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import models.Producto;
import models.ProductoUtils;

public class EliminarProductosFrame extends JFrame {
    private List<Producto> listaProductos;
    private JTable tablaProductos;

    public EliminarProductosFrame(List<Producto> listaProductos, JTable tablaProductos) {
        this.listaProductos = listaProductos;
        this.tablaProductos = tablaProductos;

        // CONFIGURAMOS VENTANA
        setTitle("Eliminación de productos");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // PANEL PRINCIPAL CON GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaciado entre componentes
        gbc.gridx = 0; // Columna inicial
        gbc.gridy = 0; // Fila inicial
        gbc.gridwidth = 2; // Ocupa dos columnas

        // TITULO CENTRADO
        JLabel lblTitulo = new JLabel("Eliminar Producto");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(lblTitulo, gbc);

        // COMBOBOX PARA ELEGIR PRODUCTO
        gbc.gridy++;
        gbc.gridwidth = 1; // Reset gridwidth
        gbc.anchor = GridBagConstraints.LINE_END;
        JLabel lblProducto = new JLabel("Nombre del producto: ");
        mainPanel.add(lblProducto, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        JComboBox<String> comboProductos = new JComboBox<>();
        for (Producto producto : listaProductos) {
            comboProductos.addItem(producto.getNombre());
        }
        mainPanel.add(comboProductos, gbc);

        // BOTON DE ELIMINAR
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2; // Ocupa dos columnas
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnEliminar = new JButton("Eliminar");
        mainPanel.add(btnEliminar, gbc);

        // ETIQUETA DE ERROR
        gbc.gridy++;
        JLabel lblError = new JLabel("", JLabel.CENTER);
        lblError.setForeground(Color.RED);
        mainPanel.add(lblError, gbc);

        // AÑADIMOS EL PANEL PRINCIPAL
        add(mainPanel, BorderLayout.CENTER);

        // ACCIÓN DEL BOTON ELIMINAR
        btnEliminar.addActionListener(e -> {
            int index = comboProductos.getSelectedIndex();
            if (index >= 0) {
                // CONFIRMAMOS LA ELIMINACIÓN
                int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar el producto?", "Confirmación", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    // ELIMINAMOS EL PRODUCTO DE LA LISTA
                    listaProductos.remove(index);

                    // REFLEJAMOS LOS CAMBIOS EN LA TABLA
                    ((DefaultTableModel) tablaProductos.getModel()).removeRow(index);

                    // ACTUALIZAMOS EL COMBOBOX
                    comboProductos.removeItemAt(index);

                    //GUARDAMOS LOS CAMBIOS EN EL ARCHIVO
                    ProductoUtils.guardarProductosDesdeArchivo(listaProductos, "productos.csv");

                    lblError.setText(""); // Limpiar mensaje de error
                    JOptionPane.showMessageDialog(this, "Producto eliminado correctamente", "Eliminación exitosa", JOptionPane.INFORMATION_MESSAGE);

                    // CERRAR LA VENTANA SI YA NO QUEDAN PRODUCTOS
                    if (comboProductos.getItemCount() == 0) {
                        dispose();
                    }
                }
            } else {
                lblError.setText("Selecciona un producto válido para eliminar.");
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
