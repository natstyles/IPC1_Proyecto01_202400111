//LIBRERIAS
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import models.Cliente;
import models.ClientesUtils;

public class EditarClienteFrame extends JFrame{
    private List<Cliente> listaClientes;
    private JTable tablaClientes;

    public EditarClienteFrame(List<Cliente> listaClientes, JTable tablaClientes){
        this.listaClientes = listaClientes;
        this.tablaClientes = tablaClientes;

        //CONFIGURAMOS VENTANA
        this.setTitle("Editar Cliente");
        this.setSize(400, 250);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //PANEL PRINCIPAL
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //TITULO
        JLabel lblTitulo = new JLabel("Editar Cliente");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(lblTitulo);

        mainPanel.add(Box.createVerticalStrut(20));

        //COMBOBOX PARA ELEGIR CLIENTE
        JPanel clientePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblCliente = new JLabel("Cliente: ");
        JComboBox<String> comboClientes = new JComboBox<>();
        for(Cliente cliente : listaClientes){
            comboClientes.addItem(cliente.getNombre());
        }
        clientePanel.add(lblCliente);
        clientePanel.add(comboClientes);
        mainPanel.add(clientePanel);

        //CAMPO DE NIT
        JPanel nitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblNit = new JLabel("NIT: ");
        JTextField txtNit = new JTextField(15);
        nitPanel.add(lblNit);
        nitPanel.add(txtNit);
        mainPanel.add(nitPanel);

        //BOTON PARA EDITAR
        JButton btnEditar = new JButton("Editar cliente");
        btnEditar.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(btnEditar);

        //ETIQUETA DE ERROR
        JLabel lblError = new JLabel("", JLabel.CENTER);
        lblError.setForeground(Color.RED);
        lblError.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblError);

        add(mainPanel, BorderLayout.CENTER);

        //ACCIÓN DEL BOTON EDITAR
        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = comboClientes.getSelectedIndex();
                if(index >= 0){
                    String nuevoNit = txtNit.getText().trim();

                    //MARCAR C/F SI EL NIT ESTÁ VACÍO
                    if (nuevoNit.isEmpty()){
                        nuevoNit = "C/F";
                    }

                    //VERIFICAMOS QUE EL NIT NO ESTÉ REPETIDO
                    for(int i = 0; i < listaClientes.size(); i++){
                        if(i != index && listaClientes.get(i).getNit().equals(nuevoNit)){
                            lblError.setText("El NIT ya está registrado en otro cliente.");
                            return;
                        }
                    }

                    //ACTUALIZAMOS EL NIT DEL CLIENTE
                    Cliente clienteSeleccionado = listaClientes.get(index);
                    clienteSeleccionado.setNit(nuevoNit);

                    //ACTUALIZAMOS LA TABLA
                    DefaultTableModel modelo = (DefaultTableModel) tablaClientes.getModel();

                    //ACTUALIZAMOS EL NIT
                    modelo.setValueAt(nuevoNit, index, 1);

                    //GUARDAMOS CAMBIOS EN EL ARCHIVO
                    ClientesUtils.guardarClientesEnArchivo(listaClientes, "clientes.csv");

                    lblError.setText("");
                    JOptionPane.showMessageDialog(null, "Cliente editado correctamente.");
                    dispose();
                }else{
                    lblError.setText("Seleccione un cliente válido.");
                }
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);

    }


}
