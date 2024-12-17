//LIBRERÍAS
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import models.Producto;
import models.ProductoUtils;


public class crearProductosFrame extends JFrame{

    private JTextField txtNombre, txtPrecio, txtStock;
    private List<Producto> listaProductos;
    private JTable tablaProductos;


    public crearProductosFrame(List<Producto> listaProductos, JTable tablaProductos){
        this.listaProductos = listaProductos;
        this.tablaProductos = tablaProductos;

        //CONFIG VENTANA
        setTitle("Creación de Productos");
        setSize(420, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //PANEL PRINCIPAL
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // PANEL PARA EL TITULO (CENTRADO)
        JPanel titlePanel = new JPanel(new GridBagLayout());
        JLabel lblTitulo = new JLabel("Crear Producto");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(lblTitulo);
        mainPanel.add(Box.createVerticalStrut(5)); // Reducir espacio superior
        mainPanel.add(titlePanel);
        mainPanel.add(Box.createVerticalStrut(10)); // Reducir espacio inferior

        //CAMPO DEL NOMBRE DE PRODUCTO
        JPanel nombrePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblNombre = new JLabel("Nombre de producto: ");
        txtNombre = new JTextField(20);
        nombrePanel.add(lblNombre);
        nombrePanel.add(txtNombre);
        mainPanel.add(nombrePanel);

        //CAMPO DE PRECIO DE PRODUCTO
        JPanel precioPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblPrecio = new JLabel("Precio: ");
        txtPrecio = new JTextField(20);
        precioPanel.add(lblPrecio);
        precioPanel.add(txtPrecio);
        mainPanel.add(precioPanel);

        //CAMPO DE CANTIDAD DE PRODUCTO
        JPanel stockPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblStock = new JLabel("Cantidad: ");
        txtStock = new JTextField(20);
        stockPanel.add(lblStock);
        stockPanel.add(txtStock);
        mainPanel.add(stockPanel);

        //BOTON PARA CREAR PRODUCTO
        JButton btnCrear = new JButton("Crear Producto");
        btnCrear.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(10)); // Espacio antes del botón
        mainPanel.add(btnCrear);

        //ETIQUETA DE ERROR
        JLabel lblError = new JLabel("", JLabel.CENTER);
        lblError.setForeground(Color.RED);
        lblError.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblError);

        //AÑADIR PANEL PRINCIPAL A LA VENTANA
        add(mainPanel);

        //CENTRAR Y MOSTRAR VENTANA
        setLocationRelativeTo(null);
        setVisible(true);

        //EVENTO DE BOTON CREAR PRODUCTO
        btnCrear.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String nombre = txtNombre.getText().trim();
                String precioTexto = txtPrecio.getText().trim();
                String stockTexto = txtStock.getText().trim();

                //VALIDANDO CAMPOS
                if(nombre.isEmpty() || precioTexto.isEmpty() || stockTexto.isEmpty()){
                    lblError.setText("Todos los campos son requeridos");
                    return;
                }

                double precio;
                int stock;

                try{
                    precio = Double.parseDouble(precioTexto);
                    if(precio <=0){
                        lblError.setText("El precio debe ser mayor a 0");
                        return;
                    }
                }catch (NumberFormatException ex){
                    lblError.setText("El precio debe ser un número válido");
                    return;
                }

                try{
                    stock = Integer.parseInt(stockTexto);
                    if(stock < 0){
                        lblError.setText("El stock no puede ser negativo");
                        return;
                    }
                }catch (NumberFormatException ex){
                    lblError.setText("El stock debe ser un número entero válido");
                    return;
                }

                //VERIFICAR SI YA EXISTE EL PRODUCTO
                for(Producto producto : listaProductos){
                    if(producto.getNombre().equalsIgnoreCase(nombre)){
                        lblError.setText("El producto ya existe en el inventario");
                        return;
                    }
                }

                //GUARDAR PRODUCTO
                Producto nuevoProducto = new Producto(nombre, precio, stock, 0);
                listaProductos.add(nuevoProducto);

                //ACTUALIZAR TABLA
                DefaultTableModel modelo = (DefaultTableModel) tablaProductos.getModel();
                modelo.addRow(new Object[]{nombre, precio, stock});

                //GUARDAR EL PRODUCTO EN EL ARCHIVO CSV
                ProductoUtils.guardarProductosDesdeArchivo(listaProductos, "productos.csv");

                //Mostrar mensaje de éxito
                lblError.setText(""); // Limpiar mensaje de error
                JOptionPane.showMessageDialog(null, "Producto creado exitosamente:\n" +
                                "Nombre: " + nombre + "\nPrecio: Q" + precio + "\nStock: " + stock,
                        "Producto Creado", JOptionPane.INFORMATION_MESSAGE);

                //LIMPIAR CAMPOS
                txtNombre.setText("");
                txtPrecio.setText("");
                txtStock.setText("");
            }
        });

    }

    //PROBANDO EL FRAME
    public static void main(String[] args) {
        // Crear una lista de productos vacía para la prueba
        List<Producto> listaProductos = new ArrayList<>();

        // Crear un modelo de tabla básico
        String[] columnas = {"Producto", "Precio", "Stock"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable tablaProductos = new JTable(modelo);

        // Crear el frame de prueba
        new crearProductosFrame(listaProductos, tablaProductos);
    }

}
