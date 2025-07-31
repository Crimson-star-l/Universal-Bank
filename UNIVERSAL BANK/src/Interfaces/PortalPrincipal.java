package Interfaces;

import Controlador.GestorDeDatos;
import Modelo.Cuenta;
import Modelo.Transaccion;
import Modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import static Interfaces.Login.usuarioActivo;

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
    private JButton crearUnaNuevaCuentaButton;
    private JButton salirButton;
    private JButton actualizarButton;

    public PortalPrincipal() {
        actualizarTablaTransacciones(usuarioActivo.getCuentaActiva().getHistorial());//formatea el historial de transacciones me dio paja hacer una función global
        //al final si lo hice ._.
        setTitle("Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setLocationRelativeTo(null);

        setContentPane(panelprincipal);

        //Este es el icono del usuario la imagen circula que esta en gris
        ImageIcon iconousuario = new ImageIcon(getClass().getResource("/Util/Imagenes/usericon.jpg"));
        Image iconoescalado = iconousuario.getImage().getScaledInstance(125, 125, Image.SCALE_SMOOTH);
        usericon.setIcon(new ImageIcon(iconoescalado));

        Usuario usuarioactivo = usuarioActivo; //obtiene el usuario que está conectado actualemente
        if (usuarioactivo != null) {// y despliega los datos de cuenta
            lbnombre.setText(usuarioactivo.getNombre());
            lbcorreo.setText(usuarioactivo.getCorreo());
            lbtelefono.setText(usuarioactivo.getTelefono());
            lbcedula.setText(usuarioactivo.getCedula());
        }

        Cuenta cuentaActual = usuarioactivo.getCuentaActiva();//lo mismo despliega los datos solo que de la cuenta activa
        if (cuentaActual != null) {
            lbsaldo.setText(String.format("%.2f", cuentaActual.getSaldo()));
            lbcuentanum.setText(cuentaActual.getNumerodecuenta());
            lbtipocuenta.setText(cuentaActual.getTipo().toString());
        }

        List<Transaccion> transacciones = usuarioactivo.getTransacciones();// obtiene el historial de transacciones y despliega sus datos en el jtable
        String[] columnas = {"Tipo", "Monto", "Fecha", "Cuenta destino"};
        Object[][] datos = new Object[transacciones.size()][columnas.length];
        /*Explicación breve de que es Object ya que está bien interesante al parecer la clase Object es una superclase de todas las clases
         * algo asi como la raíz de un arbol entonces indicamos en esa linea de arriba que la variable dato puede trabajar con cualquier
         * tipo de variable así sea un int o String o una clase como Usuario, etc en pocas palabras es una clase genérica más o menos
         * */
        for (int i = 0; i < transacciones.size(); i++) {
            Transaccion t = transacciones.get(i);
            datos[i][0] = t.getTipo().toString();
            datos[i][1] = String.format("$%.2f", t.getMonto());
            datos[i][2] = t.getFechaHora();
            datos[i][3] = t.getCuentaDestino() != null ? t.getCuentaDestino() : "-";
        }

        tbhistorial = new JTable(datos, columnas);

        tbhistorial = new JTable(datos,columnas);
        JScrollPane scrollPane = new JScrollPane(tbhistorial);
        scrollPane.setPreferredSize(new Dimension(600, 300));//tamaño del panel :b

        paneltransacciones.setLayout(new BorderLayout());

        jbtitulo = new JLabel("--Historial de Transacciones--");
        jbtitulo.setFont(new Font("Arial", Font.BOLD, 18));
        jbtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        paneltransacciones.add(jbtitulo, BorderLayout.NORTH);

        paneltransacciones.add(scrollPane, BorderLayout.CENTER);
        setVisible(true);

        verificarEstadoCuentas();

        depostiobt.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Ingrese el monto a depositar:");
            if (input != null && !input.trim().isEmpty()) {
                try {
                    double monto = Double.parseDouble(input);
                    Usuario usuario = usuarioActivo;
                    Cuenta cuenta = usuario.getCuentaActiva();

                    if (cuenta != null) {
                        boolean exito = GestorDeDatos.depositar(usuario, cuenta, monto);
                        if (exito) {

                            lbsaldo.setText(String.format("%.2f", cuentaActual.getSaldo()));

                            actualizarTablaTransacciones(usuario.getTransacciones());
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Ingrese un número válido", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        retiroButton.addActionListener(e -> {
            Usuario usuario = usuarioActivo;
            if (usuario == null) return;

            Cuenta cuenta = usuario.getCuentaActiva();
            if (cuenta == null) {
                JOptionPane.showMessageDialog(null, "No hay cuenta activa.");
                return;
            }

            String input = JOptionPane.showInputDialog(null, "Ingrese el monto a retirar:");
            if (input != null) {
                try {
                    double monto = Double.parseDouble(input);

                    boolean exito = GestorDeDatos.retirar(usuario, cuenta, monto);


                    if (exito) {

                        lbsaldo.setText(String.format("%.2f", cuentaActual.getSaldo()));

                        actualizarTablaTransacciones(cuenta.getHistorial());
                    }


                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Ingrese un número válido.");
                }
            }

        });

        transferenciaButton.addActionListener(e -> {
            String cuentaOrigen = JOptionPane.showInputDialog("Ingrese su número de cuenta de origen:");
            String cuentaDestino = JOptionPane.showInputDialog("Ingrese el número de cuenta destino:");
            String montoStr = JOptionPane.showInputDialog("Ingrese el monto a transferir:");

            if (cuentaOrigen == null || cuentaDestino == null || montoStr == null) return;

            try {
                double monto = Double.parseDouble(montoStr);

                boolean exito = GestorDeDatos.transferir(usuarioActivo, cuentaOrigen, cuentaDestino, monto);

                if (exito) {
                    actualizarTablaTransacciones(usuarioActivo.getTransacciones());
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Monto inválido. Intente de nuevo.");
            }
        });

        pagarServiciosButton.addActionListener(e -> {
            String[] servicios = {"Agua", "Luz", "Internet", "Teléfono", "Gas", "Otro"};
            String tipoServicio = (String) JOptionPane.showInputDialog(
                    null,
                    "Seleccione el tipo de servicio:",
                    "Pago de Servicio",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    servicios,
                    servicios[0]
            );

            if (tipoServicio == null) return;

            String cuenta = JOptionPane.showInputDialog("Ingrese su número de cuenta para el pago:");
            if (cuenta == null) return;

            String montoStr = JOptionPane.showInputDialog("Ingrese el monto a pagar:");
            if (montoStr == null) return;

            try {
                double monto = Double.parseDouble(montoStr);

                boolean exito = GestorDeDatos.pagarServicio(usuarioActivo, cuenta, monto, tipoServicio);

                if (exito) {
                    actualizarTablaTransacciones(usuarioActivo.getTransacciones());
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Monto inválido.");
            }
        });

        crearUnaNuevaCuentaButton.addActionListener(e -> {
            List<Cuenta> cuentas = usuarioActivo.getCuentas();
            boolean tieneAhorro = cuentas.stream().anyMatch(c -> c.getTipo() == Cuenta.TipoCuenta.AHORRO);
            boolean tieneCorriente = cuentas.stream().anyMatch(c -> c.getTipo() == Cuenta.TipoCuenta.CORRIENTE);

            String tipoACrear = null;
            if (!tieneAhorro) {
                tipoACrear = "AHORRO";
            } else if (!tieneCorriente) {
                tipoACrear = "CORRIENTE";
            }

            int confirmacion = JOptionPane.showConfirmDialog(null,
                    "¿Deseas crear una cuenta de tipo " + tipoACrear + "?",
                    "Confirmación", JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                Cuenta.TipoCuenta tipoEnum = Cuenta.TipoCuenta.valueOf(tipoACrear);
                Cuenta nueva = new Cuenta(tipoEnum, 0.0);
                usuarioActivo.getCuentas().add(nueva);
                GestorDeDatos.guardarUsuarios();

                JOptionPane.showMessageDialog(null, "Cuenta " + tipoACrear + " creada exitosamente.");
                verificarEstadoCuentas(); // actualiza botones
            }
        });

        cambiarDeCuentaButton.addActionListener(e -> {
            List<Cuenta> cuentas = usuarioActivo.getCuentas();
            Cuenta cuentaActiva = usuarioActivo.getCuentaActiva();

            // Busca la otra cuenta que no es la activa
            for (int i = 0; i < cuentas.size(); i++) {
                Cuenta c = cuentas.get(i);
                if (!c.equals(usuarioActivo.getCuentaActiva())) {
                    usuarioActivo.setCuentaActiva(i); // <- aquí es donde se cambia la activa
                    GestorDeDatos.guardarUsuarios();
                    JOptionPane.showMessageDialog(null, "Has cambiado a tu cuenta " + c.getTipo());
                    break;
                }
            }


            // Actualiza el panel principal porque si no se queda con el historial de transacciones de la otra cuenta
            actualizarPanel();
        });

        salirButton.addActionListener(e -> {
            actualizarTablaTransacciones(transacciones);
            GestorDeDatos.guardarUsuarios();
            System.exit(0);
        });

        actualizarButton.addActionListener(e -> {
            actualizarTablaTransacciones(transacciones);
        });

        addWindowListener(new WindowAdapter() {//Esto lo acabo de descubrir en stackoverflow xD
            @Override//En pocas palabras no quise resolver un bug y entonces decidí mejor capturar el evento LOL
            public void windowClosing(WindowEvent e) {//cuando se sale por el exit_on_close (boton de x) guarda los usuarios y actualiza la tabla que era el bug
                actualizarTablaTransacciones(transacciones);
                GestorDeDatos.guardarUsuarios(); //
            }
        });
    }
    private void actualizarTablaTransacciones(List<Transaccion> transacciones) {
        String[] columnas = {"Tipo", "Monto", "Fecha", "Cuenta destino"};
        Object[][] datos = new Object[transacciones.size()][columnas.length];

        for (int i = 0; i < transacciones.size(); i++) {
            Transaccion t = transacciones.get(i);

            // Determinar si el monto es ingreso o egreso
            String montoFormateado;
            switch (t.getTipo()) {
                case DEPOSITO:
                    montoFormateado = "$" + String.format("%.2f", t.getMonto());
                    break;
                case RETIRO:
                case PAGO_SERVICIO:
                    montoFormateado = "- $" + String.format("%.2f", t.getMonto());
                    break;
                case TRANSFERENCIA:
                    // Ver si fue enviada o recibida según el usuario activo
                    if (t.getCuentaOrigen().equals(usuarioActivo.getCuentaActiva().getNumerodecuenta())) {
                        montoFormateado = "- $" + String.format("%.2f", t.getMonto());
                    } else {
                        montoFormateado = "+$" + String.format("%.2f", t.getMonto());
                    }
                    break;
                default:
                    montoFormateado = "$" + String.format("%.2f", t.getMonto());
                    break;
            }

            datos[i][0] = t.getTipo().toString();
            datos[i][1] = montoFormateado;
            datos[i][2] = t.getFechaFormateada();
            datos[i][3] = (t.getCuentaDestino() != null) ? t.getCuentaDestino() : "-";
        }

        tbhistorial.setModel(new javax.swing.table.DefaultTableModel(datos, columnas));//set hmm ? establece el modelo de diseño del jtable
    }

    private void verificarEstadoCuentas() {
        List<Cuenta> cuentas = usuarioActivo.getCuentas();

        boolean tieneAhorro = false;
        boolean tieneCorriente = false;

        for (Cuenta c : cuentas) {
            if (c.getTipo() == Cuenta.TipoCuenta.AHORRO) {
                tieneAhorro = true;
            } else if (c.getTipo() == Cuenta.TipoCuenta.CORRIENTE) {
                tieneCorriente = true;
            }
        }

        cambiarDeCuentaButton.setEnabled(tieneAhorro && tieneCorriente);
        crearUnaNuevaCuentaButton.setEnabled(!(tieneAhorro && tieneCorriente));
    }

    private void actualizarPanel() {
        Cuenta cuentaActiva = usuarioActivo.getCuentaActiva();
        if (cuentaActiva == null) return;

        // Actualiza campos visibles con formato correcto para saldo
        lbsaldo.setText("Saldo: $" + String.format("%.2f", cuentaActiva.getSaldo()));
        lbcuentanum.setText(cuentaActiva.getNumerodecuenta());
        lbtipocuenta.setText("Tipo: " + cuentaActiva.getTipo());

        // Actualiza tabla de transacciones
        actualizarTablaTransacciones(cuentaActiva.getHistorial());
    }
}