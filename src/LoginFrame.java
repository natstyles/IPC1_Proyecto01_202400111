//Librerias principales
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Clase principal
public class LoginFrame extends JFrame{

    //Constructor
    public LoginFrame(){
        //TITULO
        setTitle("Iniciar Sesión - Inventario del Dojo Cobra Kai");
        setSize(400, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //PANEL INICIAL
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //LABEL DEL TITULO
        JLabel lblTitulo = new JLabel("Iniciar Sesión", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblTitulo);

        // Campo de usuario
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        JLabel lblUsuario = new JLabel("Usuario: ");
        JTextField txtUsuario = new JTextField();
        lblUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        userPanel.add(lblUsuario);
        userPanel.add(txtUsuario);
        panel.add(userPanel);

   

        // Etiqueta de error
        JLabel lblError = new JLabel("", JLabel.CENTER);
        lblError.setForeground(Color.RED);
        lblError.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblError);

        //AÑADIR PANEL PRINCIPAL A LA VENTANA
        add(panel, BorderLayout.CENTER);

        //EVENTO DE BOTON INICIAR SESIÓN
        btnLogin.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText();
                String pass = new String(txtPassword.getPassword());

                //VALIDANDO CREDENCIALES
                if(usuario.equals("skibidi") && pass.equals("toilet")){
                    JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso", "Bienvenido!", JOptionPane.INFORMATION_MESSAGE);
                    //CERRAMOS VENTANA DE LOGIN
                    dispose();

                    //ABRIENDO VENTANA DE ADMINISTRADOR
                    new AdminFrame();
                }else{
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos, intentalo de nuevo", "Inicio de sesión fallido", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //MOSTRAMOS Y CENTRAMOS LA VENTANA
        setVisible(true);
        setLocationRelativeTo(null);


    }

    //PROBAMOS VENTANA
    public static void main(String[] args) {
        new LoginFrame();
    }
}