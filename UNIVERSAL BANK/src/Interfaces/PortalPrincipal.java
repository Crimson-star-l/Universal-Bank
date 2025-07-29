package Interfaces;

import Modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class PortalPrincipal extends JFrame {
    private JPanel panelprincipal;
    private JLabel usericon;
    private JPanel panelusuario;
    private JLabel lbnombre;
    private JLabel lbcorreo;
    private JLabel lbtelefono;
    private JLabel lbcedula;

    public PortalPrincipal() {
        setTitle("Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,1000);
        setLocationRelativeTo(null);

        ImageIcon iconousuario = new ImageIcon(getClass().getResource("/Util/Imagenes/usericon.jpg"));
        Image iconoescalado = iconousuario.getImage().getScaledInstance(125, 125, Image.SCALE_SMOOTH);
        usericon.setIcon(new ImageIcon(iconoescalado));

        panelprincipal.add(panelusuario);
        panelprincipal.add(usericon,BorderLayout.NORTH);
        setContentPane(panelprincipal);
        setVisible(true);
        
        Usuario usuarioactivo = Login.usuarioActivo;
        lbnombre.setText(usuarioactivo.getNombre());
        lbcorreo.setText(usuarioactivo.getCorreo());
        lbtelefono.setText(usuarioactivo.getTelefono());
        lbcedula.setText(usuarioactivo.getCedula());



    }
}
