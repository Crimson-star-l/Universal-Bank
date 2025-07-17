package Interfaces;
import Util.Clases.PanelConFondo;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    private JPanel panellogin;


    public  Login() {
        PanelConFondo panelConFondo = new PanelConFondo("src/Util/Imagenes/fondoproyecto.png");

        panelConFondo.setLayout(new BorderLayout());

        panelConFondo.add(panellogin, BorderLayout.CENTER);
        panellogin.setOpaque(false);

        setContentPane(panelConFondo);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350,450);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
