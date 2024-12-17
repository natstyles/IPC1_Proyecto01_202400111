//LIBRERIAS
import javax.swing.*;
import java.awt.*;
import java.util.List;
import models.Cliente;


public class ModuloClientesFrame extends JFrame{
    private List<Cliente> listaClientes;
    private JTable tablaClientes;

    public ModuloClientesFrame(List<Cliente> listaClientes, JTable tablaClientes){
        this.listaClientes = listaClientes;
        this.tablaClientes = tablaClientes;

        //CONFIGURAMOS VENTANA
        setTitle("Módulo Clientes");
        setSize(400, 230);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //PANEL PRINCIPAL
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //TITULO
        JLabel lblTitulo = new JLabel("Módulo Clientes", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(lblTitulo);

        mainPanel.add(Box.createVerticalStrut(20)); //ESPACIO VERTICAL

        //BOTONES
        JButton btnCrearCliente = new JButton("Crear cliente");
        JButton btnEditarCliente = new JButton("Editar cliente");

        btnCrearCliente.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEditarCliente.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(btnCrearCliente);
        mainPanel.add(Box.createVerticalStrut(10)); //ESPACIO VERTICAL
        mainPanel.add(btnEditarCliente);

        //BOTON DE VOLVER
        JButton btnVolver = new JButton("Volver al módulo de administración");
        btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVolver.addActionListener(e -> dispose());

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(btnVolver);

        //EVENTOS DE BOTONES

        //CREAR CLIENTE
        btnCrearCliente.addActionListener(e -> new CrearClienteFrame(listaClientes, tablaClientes));

        //EDITAR CLIENTE
        btnEditarCliente.addActionListener(e -> {
            new EditarClienteFrame(listaClientes, tablaClientes);
        });

        //AÑADIR PANEL PRINCIPAL
        add(mainPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
