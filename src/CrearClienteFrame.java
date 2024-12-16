//LIBRERIAS
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import models.Cliente;
import models.ClientesUtils;

public class CrearClienteFrame extends JFrame{
    private JTextField txtNombre, txtNit;
    private List<Cliente> listaClientes;
    private JTable tablaClientes;

    public CrearClienteFrame(List<Cliente> listaClientes, JTable tablaClientes){
        this.listaClientes = listaClientes;
        this.tablaClientes = tablaClientes;

        //CONFIGURAMOS VENTANA
        setTitle("Crear cliente");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //PANEL PRINCIPAL
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //CAMPOS DE ENTRADA
        JPanel nombrePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblNombre = new JLabel("Nombre del cliente: ");
        txtNombre = new JTextField(15);
        nombrePanel.add(lblNombre);
        nombrePanel.add(txtNombre);
        mainPanel.add(nombrePanel);

        JPanel nitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblNit = new JLabel("NIT: ");
        txtNit = new JTextField(15);
        nitPanel.add(lblNit);
        nitPanel.add(txtNit);
        mainPanel.add(nitPanel);

        //BOTÓN DE CREAR
        JButton btnCrear = new JButton("Crear cliente");
        btnCrear.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(10)); //ESPACIO VERTICAL
        mainPanel.add(btnCrear);

        JLabel lblError = new JLabel("", JLabel.CENTER);
        lblError.setForeground(Color.RED);
        mainPanel.add(lblError);

        add(mainPanel, BorderLayout.CENTER);

        // EVENTO DEL BOTÓN
        btnCrear.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String nit = txtNit.getText().trim();

            if (nombre.isEmpty()) {
                lblError.setText("El nombre del cliente es requerido.");
                return;
            }

            if (nit.isEmpty()) {
                nit = "C/F"; // Si el NIT está vacío, asignar C/F
            }

            // VERIFICAR SI YA EXISTE EL NIT
            for (Cliente cliente : listaClientes) {
                if (cliente.getNit().equalsIgnoreCase(nit)) { // Comparación por NIT
                    lblError.setText("El NIT ya existe en el sistema.");
                    return;
                }
            }

            // GUARDAR CLIENTE
            Cliente nuevoCliente = new Cliente(nombre, nit, 0); // 0 Compras realizadas
            listaClientes.add(nuevoCliente);

            // ACTUALIZAR TABLA
            DefaultTableModel modelo = (DefaultTableModel) tablaClientes.getModel();
            modelo.addRow(new Object[]{nombre, nit, 0});

            // GUARDAR CLIENTE EN EL ARCHIVO CSV
            ClientesUtils.guardarClientesEnArchivo(listaClientes, "clientes.csv");

            JOptionPane.showMessageDialog(this, "Cliente creado exitosamente.");
            dispose();
        });


        setLocationRelativeTo(null);
        setVisible(true);
    }
}
