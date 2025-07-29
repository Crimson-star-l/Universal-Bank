package Interfaces;

import Controlador.GestorDeDatos;
import Modelo.Cuenta;
import Modelo.Usuario;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Registro extends JFrame {
    private JTextField textFieldcorreo;
    private JTextField textFieldnombre;
    private JTextField textFieldcedula;
    private JTextField textFieldtelefono;
    private JTextField textFieldfecha;
    private JComboBox comboBoxtipocuenta;
    private JButton registrarButton;
    private JPanel Registro;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;

    public Registro() {
        setTitle("Registro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,700);
        setLocationRelativeTo(null);
        setVisible(true);
        setContentPane(Registro);


        registrarButton.addActionListener(actionEvent -> {
            String nombre = textFieldnombre.getText();
            if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")){
                JOptionPane.showMessageDialog(null, "Ingrese un nombre valido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String correo = textFieldcorreo.getText();
            if (!correo.matches("^[\\w.-]+@(gmail|hotmail|yahoo)\\.com$")){//Reguex personalizado que en caso de no terminar en alguno de esos dominios manda un error :b
                JOptionPane.showMessageDialog(null, "Ingrese un correo valido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String cedula = textFieldcedula.getText();
            if (!cedula.matches("[0-9-]+")){
                JOptionPane.showMessageDialog(null, "La identificación solo debe tener números y guiones", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (cedula.length()>12){
                JOptionPane.showMessageDialog(null,"Ingrese un Documento de Identificación válido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!cedula.matches("\\d+-\\d+-\\d+")){
                JOptionPane.showMessageDialog(null,"Ingrese un Documento de Identificación valido 0-0000-000", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String telefono = textFieldtelefono.getText();
            if (!telefono.matches("^\\d{4}-\\d{4}$")){
                JOptionPane.showMessageDialog(null,"Ingrese un número valido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String fecha = textFieldfecha.getText();
            if (!fecha.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
                JOptionPane.showMessageDialog(null, "La fecha debe tener el formato dd/mm/yyyy", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //Todo esto esta relacionado con las validaciones de la fecha desde el formato hasta un año base :b
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setLenient(false);
                Date fechaNacimiento = sdf.parse(fecha);

                // Validar que el año esté entre 1900 y el actual
                Calendar cal = Calendar.getInstance();
                cal.setTime(fechaNacimiento);
                int anio = cal.get(Calendar.YEAR);
                int anioActual = Calendar.getInstance().get(Calendar.YEAR);

                if (anio < 1900 || anio > anioActual) {
                    JOptionPane.showMessageDialog(null, "El año debe estar entre 1900 y " + anioActual, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (ParseException e){
                JOptionPane.showMessageDialog(null, "Ingrese una fecha valida", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String clave = passwordField1.getText();
            String clave2 = passwordField2.getText();

            if (!clave.equals(clave2)){
                JOptionPane.showMessageDialog(null, "La contraseñas no coinciden","Error", JOptionPane.ERROR_MESSAGE);
            }

            //crea el usuario del pibe
            boolean creado = GestorDeDatos.crearUsuario(nombre,correo,clave,cedula,telefono,fecha);
            if (!creado){return;}//en caso de que no se cree esta mamada :b
            Usuario nuevoUsuario = GestorDeDatos.buscarUsuarioCorreo(correo);

            //Crea la cuenta segun el tipo que elije el pibe
            String tipo = comboBoxtipocuenta.getSelectedItem().toString();
            Cuenta.TipoCuenta tipoCuenta = Cuenta.TipoCuenta.valueOf(tipo);

            boolean cuentacreada = GestorDeDatos.agregarCuenta(nuevoUsuario,tipoCuenta);
            if (!cuentacreada){return;}//lo mismo que arriba en la linea 104 .-.

            JOptionPane.showMessageDialog(null,"Usuario creado exitosamente");

            GestorDeDatos.guardarUsuarios();
            new Login();
            dispose();
        });

    }
}
