//LIBRERIAS
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import models.Producto;
import models.ProductoUtils;

public class EditarProductoFrame extends JFrame{
    private List<Producto> listaProductos;
    private JTable tablaProductos;

    public EditarProductoFrame(List<Producto> listaProductos, JTable tablaProductos){
        this.listaProductos = listaProductos;
        this.tablaProductos = tablaProductos;

        //CONFIGURAMOS VENTANA
        setTitle("Edición de productos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //PANEL PRINCIPAL
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //TITULO
        JLabel lblTitulo = new JLabel("Edición de productos", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(lblTitulo, gbc);

        //COMBOBOX PARA ELEGIR PRODUCTO
        JLabel lblNombre = new JLabel("Producto: ");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(lblNombre, gbc);

        JComboBox<String> comboProductos = new JComboBox<>();
        for(Producto producto : listaProductos){
            comboProductos.addItem(producto.getNombre());
        }
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(comboProductos, gbc);

        //CAMPO DE PRECIO
        JLabel lblPrecio = new JLabel("Precio: ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(lblPrecio, gbc);

        JTextField txtPrecio = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(txtPrecio, gbc);

        //CAMPO DE STOCK
        JLabel lblStock = new JLabel("Stock: ");
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(lblStock, gbc);

        JTextField txtStock = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 3;
        mainPanel.add(txtStock, gbc);

        //BOTON DE GUARDAR EDICIÓN
        JButton btnEditar = new JButton("Editar Producto");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(btnEditar, gbc);

        //ETIQUETA DE ERROR
        JLabel lblError = new JLabel("", JLabel.CENTER);
        lblError.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        mainPanel.add(lblError, gbc);

        add(mainPanel, BorderLayout.CENTER);

        //ACCION AL ELEGIR UN PRODUCTO DEL COMBOBOX
        btnEditar.addActionListener(e -> {
            String productoSeleccionado = (String) comboProductos.getSelectedItem(); // Obtener nombre del producto
            if (productoSeleccionado != null) {
                try {
                    double nuevoPrecio = Double.parseDouble(txtPrecio.getText().trim());
                    int nuevoStock = Integer.parseInt(txtStock.getText().trim());

                    if (nuevoPrecio <= 0) {
                        lblError.setText("El precio debe ser mayor a 0");
                        return;
                    }

                    if (nuevoStock < 0) {
                        lblError.setText("El stock no puede ser negativo");
                        return;
                    }

                    // Buscar el producto por nombre
                    for (Producto producto : listaProductos) {
                        if (producto.getNombre().equals(productoSeleccionado)) {
                            producto.setPrecio(nuevoPrecio);
                            producto.setStock(nuevoStock);
                            break;
                        }
                    }

                    // Reflejar cambios en la tabla
                    DefaultTableModel modelo = (DefaultTableModel) tablaProductos.getModel();
                    for (int i = 0; i < listaProductos.size(); i++) {
                        Producto producto = listaProductos.get(i);
                        if (producto.getNombre().equals(productoSeleccionado)) {
                            modelo.setValueAt(nuevoPrecio, i, 1);
                            modelo.setValueAt(nuevoStock, i, 2);
                            break;
                        }
                    }

                    // Guardar cambios en el archivo CSV
                    ProductoUtils.guardarProductosDesdeArchivo(listaProductos, "productos.csv");

                    lblError.setText("");
                    JOptionPane.showMessageDialog(this, "Producto editado exitosamente");
                    dispose();
                } catch (NumberFormatException ex) {
                    lblError.setText("Precio y stock deben ser números válidos");
                }
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);

    }

}
