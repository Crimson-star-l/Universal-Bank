package Interfaces;

import Util.Clases.PanelConFondo;

import javax.swing.*;
import java.awt.*;
public class Login extends JFrame {

    private JPanel panellogin;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton iniciarSesionButton;
    private JButton registrarseButton;

    public Login() {

        setTitle("Login");
        PanelConFondo panelConFondo = new PanelConFondo("UNIVERSAL BANK/src/Util/Imagenes/fondoproyecto.png");
        panelConFondo.setLayout(new BorderLayout());

        panelConFondo.add(panellogin, BorderLayout.CENTER);
        panellogin.setOpaque(false);

        setContentPane(panelConFondo);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350,500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Login();
    }
}
