package Util.Clases;
import javax.swing.*;
import java.awt.*;
public class PanelConFondo extends JPanel{

    private Image fondo;

    public PanelConFondo(String ruta) {
        ImageIcon imagen = new ImageIcon(ruta);
        fondo = imagen.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fondo != null) {
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
