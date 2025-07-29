package Interfaces;

import Modelo.Cuenta;
import Modelo.Transaccion;
import Modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PortalPrincipal extends JFrame {
    private JPanel panelprincipal;
    private JLabel usericon;
    private JLabel lbnombre;
    private JLabel lbcorreo;
    private JLabel lbtelefono;
    private JLabel lbcedula;
    private JPanel panelusuario;
    private JButton depostiobt;
    private JButton retiroButton;
    private JButton transferenciaButton;
    private JButton pagarServiciosButton;
    private JButton cambiarDeCuentaButton;
    private JLabel lbsaldo;
    private JLabel lbcuentanum;
    private JLabel lbtipocuenta;
    private JPanel panelcuenta;
    private JPanel paneltransacciones;
    private JTable tbhistorial;
    private JLabel jbtitulo;

    public PortalPrincipal() {
        setTitle("Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setLocationRelativeTo(null);

        setContentPane(panelprincipal);

        ImageIcon iconousuario = new ImageIcon(getClass().getResource("/Util/Imagenes/usericon.jpg"));
        Image iconoescalado = iconousuario.getImage().getScaledInstance(125, 125, Image.SCALE_SMOOTH);
        usericon.setIcon(new ImageIcon(iconoescalado));

        Usuario usuarioactivo = Login.usuarioActivo;
        if (usuarioactivo != null) {
            lbnombre.setText(usuarioactivo.getNombre());
            lbcorreo.setText(usuarioactivo.getCorreo());
            lbtelefono.setText(usuarioactivo.getTelefono());
            lbcedula.setText(usuarioactivo.getCedula());
        }

        Cuenta cuentaActual = usuarioactivo.getCuentaActiva();
        if (cuentaActual != null) {
            lbsaldo.setText(String.valueOf(cuentaActual.getSaldo()));
            lbcuentanum.setText(cuentaActual.getNumerodecuenta());
            lbtipocuenta.setText(cuentaActual.getTipo().toString());
        }

        List<Transaccion> transacciones = usuarioactivo.getTransacciones();
        String[] columnas = {"Tipo", "Monto", "Fecha", "Cuenta destino"};//son las columnas de la lista...lo que se ven en el historial .-.
        Object[][] datos = new Object[transacciones.size()][columnas.length];
        /*Explicación breve de que es Object ya que está bien interesante al parecer la clase Object es una superclase de todas las clases
        * algo asi como la raíz de un arbol entonces indicamos en esa linea de arriba que la variable dato puede trabajar con cualquier
        * tipo de variable así sea un int o String o una clase como Usuario, etc en pocas palabras es una clase genérica más o menos
        * */
        tbhistorial = new JTable(datos,columnas);
        JScrollPane scrollPane = new JScrollPane(tbhistorial);
        scrollPane.setPreferredSize(new Dimension(600, 300));//tamaño del panel :b

        paneltransacciones.setLayout(new BorderLayout());

        jbtitulo = new JLabel("Historial de Transacciones");
        jbtitulo.setFont(new Font("Arial", Font.BOLD, 14));
        jbtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        paneltransacciones.add(jbtitulo, BorderLayout.NORTH);

        paneltransacciones.add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }
    }