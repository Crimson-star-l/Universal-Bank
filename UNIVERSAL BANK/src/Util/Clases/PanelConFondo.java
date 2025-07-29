package Util.Clases;

import javax.swing.*;
import java.awt.*;

public class PanelConFondo extends JPanel {
    private Image fondo;

    public PanelConFondo(String ruta) {
        try {
            // Intenta cargar desde el classpath (src)
            ImageIcon imagen = new ImageIcon(getClass().getResource(ruta));
            fondo = imagen.getImage();
        } catch (Exception e) {
            System.out.println("No se pudo cargar la imagen: " + ruta);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fondo != null) {
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
