package Interfaces;

import Controlador.GestorDeDatos;

import javax.swing.*;
import java.awt.*;

public class Presentacion extends JFrame {
    private JPanel panelpresentacion;
    private JLabel logoutp;
    private JLabel logofisc;

    public Presentacion() {
        setTitle("Presentación");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        // Cargar imágenes desde recursos
        ImageIcon logouni = new ImageIcon(getClass().getResource("/Util/Imagenes/logoUniversidad.png"));
        Image logoescalado = logouni.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        logoutp.setIcon(new ImageIcon(logoescalado));

        ImageIcon logofiscImg = new ImageIcon(getClass().getResource("/Util/Imagenes/logoFISC.png"));
        Image logofiscescalado = logofiscImg.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        this.logofisc.setIcon(new ImageIcon(logofiscescalado));

        setContentPane(panelpresentacion);
        setVisible(true);

        setContentPane(panelpresentacion);
        setVisible(true);

        Timer timer = new Timer(2000, event -> {
            dispose();
            new Login();
        });
        timer.setRepeats(false);//Si no pongo esto genera una ventana nueva del Login cada ciertos segundos....
        timer.start();

    }

    public static void main(String[] args) {
        GestorDeDatos.cargarUsuario();
        SwingUtilities.invokeLater(Presentacion::new);
    }
}
