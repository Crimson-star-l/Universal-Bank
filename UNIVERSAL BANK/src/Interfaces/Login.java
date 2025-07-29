package Interfaces;

import Controlador.GestorDeDatos;
import Modelo.Usuario;
import Util.Clases.PanelConFondo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Login extends JFrame {

    private JPanel panellogin;
    private JTextField correoOnombretext;
    private JPasswordField passwordField1;
    private JButton iniciarSesionButton;
    private JButton registrarseButton;
    static Usuario usuarioActivo = null;

    public Login() {

        setTitle("Login");
        PanelConFondo panelConFondo = new PanelConFondo("/Util/Imagenes/fondoproyecto.png");
        panelConFondo.setLayout(new BorderLayout());

        panelConFondo.add(panellogin, BorderLayout.CENTER);
        panellogin.setOpaque(false);

        setContentPane(panelConFondo);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350,500);
        setLocationRelativeTo(null);
        setVisible(true);
        SwingUtilities.invokeLater(() -> {
            // Quita el foco del campo de texto para mostrar el texto nomas xD
            panellogin.requestFocusInWindow();
        });



        correoOnombretext.setText("Ingrese su correo o usuario");
        correoOnombretext.setForeground(Color.GRAY);

        passwordField1.setText("Ingrese su contraseña");
        passwordField1.setEchoChar((char) 0);
        passwordField1.setForeground(Color.GRAY);

        correoOnombretext.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (correoOnombretext.getText().trim().equals("Ingrese su correo o usuario")) {
                    correoOnombretext.setText("");
                    correoOnombretext.setForeground(Color.BLACK);
                }
            }

            /*Esto es bastante interesante de hecho lo acabo de descubrir xD entonces hay un focus listener
            * que se basa en un evento y en este caso el evento es que se le haga focus o se pulse en el textfield
            * mientras no se haya pulsado el textfield se mostrará el mensaje pero una vez se pulse o se haga focus eliminara el texto
            * para que se llene con las credenciales correctas*/

            public void focusLost(FocusEvent e) {
                if (correoOnombretext.getText().trim().equals("")){
                    correoOnombretext.setText("Ingrese su correo o usuario");
                    correoOnombretext.setForeground(Color.GRAY);
                }
            }
        });

        passwordField1.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField1.getPassword()).equals("Ingrese su contraseña")) {
                    passwordField1.setText("");
                    passwordField1.setEchoChar('•');
                    passwordField1.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (String.valueOf(passwordField1.getPassword()).isEmpty()) {
                    passwordField1.setText("Ingrese su contraseña");
                    passwordField1.setEchoChar((char) 0);
                    passwordField1.setForeground(Color.GRAY);
                }
            }
        });


        registrarseButton.addActionListener(e -> {
            new Registro();
            dispose();
        });


        iniciarSesionButton.addActionListener(e -> {
            String correoOnombre = correoOnombretext.getText().trim();
            String clave = passwordField1.getText().trim();

            usuarioActivo = GestorDeDatos.validarLogin(correoOnombre, clave);
            if (usuarioActivo == null) {
                JOptionPane.showMessageDialog(null, "El usuario o la contraseña es incorrecto");

            }
            else {
                JOptionPane.showMessageDialog(null, "Bienvenido, "+ usuarioActivo.getNombre());
                new PortalPrincipal();
                dispose();
            }
        });
    }


}
