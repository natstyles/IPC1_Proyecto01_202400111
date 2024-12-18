//LIBRERIAS
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import models.Cliente;
import models.Producto;
import models.VentasUtils;

public class ModuloVentasFrame extends JFrame{
    private List<Cliente> listaClientes;
    private List<Producto> listaProductos;
    private AdminFrame adminFrame;


    public ModuloVentasFrame(List<Cliente> listaClientes, List<Producto> listaProductos, AdminFrame adminFrame) {
        this.listaClientes = listaClientes;
        this.listaProductos = listaProductos;
        this.adminFrame = adminFrame;

        //CONFIGURAMOS LA VENTANA
        setTitle("Modulo de Ventas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //PANEL PRINCIPAL
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //TITULO
        JLabel lblTitulo = new JLabel("Modulo de Ventas", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblTitulo);

        mainPanel.add(Box.createVerticalStrut(20));

        //BOTON PARA REALIZAR UNA VENTA
        JButton btnRealizarVenta = new JButton("Realizar venta");
        btnRealizarVenta.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(btnRealizarVenta);

        mainPanel.add(Box.createVerticalStrut(10));


        //BOTON PARA GENERAR REPORTE HISTORICO
        JButton btnReporteHistorico = new JButton("Generar reporte de ventas histórico");
        btnReporteHistorico.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(btnReporteHistorico);

        mainPanel.add(Box.createVerticalStrut(20));

        //BOTON PARA VOLVER AL MENU PRINCIPAL
        JButton btnVolver = new JButton("Volver al módulo de administración");
        btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(btnVolver);

        //EVENTOS DE BOTONES
        btnRealizarVenta.addActionListener(e -> {
            // ABRIR VENTANA PARA REALIZAR UNA VENTA
            new RealizarVentaFrame(listaClientes, listaProductos, this.adminFrame);
        });

        btnReporteHistorico.addActionListener(e -> {
            String rutaVentasCSV = "ventas.csv";
            String rutaReporteHTML = "reporte_ventas.html";
            VentasUtils.generarReporteVentasHTML(rutaVentasCSV, rutaReporteHTML);

            // ABRIR AUTOMÁTICAMENTE EL ARCHIVO HTML
            try {
                Desktop.getDesktop().browse(new File(rutaReporteHTML).toURI());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "No se pudo abrir el reporte.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnVolver.addActionListener(e -> dispose());

        //AÑADIR PANEL PRINCIPAL A LA VENTANA
        add(mainPanel, BorderLayout.CENTER);

        //CENTRAR Y MOSTRAR
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
