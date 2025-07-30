package Controlador;

import Modelo.Cuenta;
import Modelo.Transaccion;
import Util.Clases.Adaptador;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import Modelo.Usuario;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.lang.module.FindException;
import java.lang.reflect.Type;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
//Clase 
public class GestorDeDatos {
    private static final String USUARIOS_DATA = System.getProperty("user.dir") + "/data/usuarios.json";
    private static List<Usuario> usuarios = new ArrayList<>();
        private static Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new Adaptador())
            .create();
        private static int proximoid=1;
        private static Usuario usuarioActivo;

        ///
        /// Métodos de carga y guardado
        ///  básicamente esta sección solo tiene funciones de cargar y guardar no voy a entrar en detalles solo vean el código .-.
        ///

    //Carga la base de datos de usuarios :b
    public static void cargarUsuario(){
        try {
            File archivo = new File(USUARIOS_DATA);
            if (!archivo.exists()) {//llama a la función que crea la estructura incial del proyecto
                CrearEstructuraInicial();
                return;
            }

            FileReader reader = new FileReader(archivo);
            Type tipoListaUsuarios = new TypeToken<List<Usuario>>() {}.getType();
            List<Usuario> usuarioscargados = gson.fromJson(reader, tipoListaUsuarios);

            if (usuarioscargados != null) {
                usuarios = usuarioscargados;
                proximoid = usuarios.stream()
                        .mapToInt(Usuario::getId)
                        .max()
                        .orElse(0) + 1;
            }
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, "Error al cargar usuarios: " + e.getMessage(),  "Error", JOptionPane.ERROR_MESSAGE );
            usuarios = new ArrayList<>();
        }

    }

    public static void guardarUsuarios() {
        try {
            // Crear directorios si no existen
            File archivo = new File(USUARIOS_DATA);
            archivo.getParentFile().mkdirs();

            // Escribir el arcivo de formato .json
            FileWriter writer = new FileWriter(archivo);
            gson.toJson(usuarios, writer);
            writer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar usuarios: "+e.getMessage(),   "Error", JOptionPane.ERROR_MESSAGE );
        }
    }

    //crea el archivo en caso de no existir
    public static void CrearEstructuraInicial(){
        try {
            File archivo = new File(USUARIOS_DATA);
            archivo.getParentFile().mkdirs();

            usuarios = new ArrayList<>();
            guardarUsuarios();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al crear estructura inicial: " + e.getMessage(),   "Error", JOptionPane.ERROR_MESSAGE );
        }
    }


    ///
    /// Sección de métodos de usuario
    /// en pocas palabras las cosas que se van a hacer con los usuarios (crear y buscar, etc)
    ///

    public static boolean crearUsuario(String nombre, String correo, String clave, String cedula, String telefono, String fechaNacimiento) {
        // Validar duplicados
        if (buscarUsuarioCorreo(correo) != null) {
            JOptionPane.showMessageDialog(null, "Ya existe un usuario con este correo.");
            return false;
        }

        if (buscarUsuarioCedula(cedula) != null) {
            JOptionPane.showMessageDialog(null, "Ya existe un usuario con esta cédula.");
            return false;
        }

        // Crear el usuario
        Usuario nuevo = new Usuario(proximoid, nombre, correo, clave, cedula, telefono, fechaNacimiento);
        usuarios.add(nuevo);
        proximoid++;
        guardarUsuarios();
        return true;
    }


    public static Usuario buscarUsuarioCorreo(String correo) {
        return usuarios.stream()
                .filter(usuario -> usuario.getCorreo().equals(correo))
                .findFirst()
                .orElse(null);
        //(ya me empiezo a arrepentir de elegir este proyecto (-_-)
        //Basicamente traduciendo este monstruo a lenguaje español .-. pasa lo siguienete:
        //la función de tipo "Usuario" (si basicamenete usamos la clase como un tipo de función) busca entre los usuarios las coincidencias exactas de correo
        //una vez encuentra elije la primera porque pos es la que es y en caso de no encontrar (porque no existe) retorna null porque obviamente no existe .-.

    }

    public static Usuario buscarUsuarioCedula(String cedula){
        return usuarios.stream()
                .filter(usuario -> usuario.getCedula().equals(cedula))
                .findFirst()
                .orElse(null);

        /// Este monstruos es lo mismo que el de arriba .-.
    }

    public static Usuario validarLogin(String correoOnombre, String clave) {
        for (Usuario usuario : usuarios) {// esto recorre toda la lista de usuarios
            boolean coincideCorreo = usuario.getCorreo().equals(correoOnombre);
            boolean coincideNombre = usuario.getNombre().equals(correoOnombre);
            boolean claveCorrecta = usuario.getClave().equals(clave);

            if ((coincideCorreo || coincideNombre) && claveCorrecta) {
                return usuario;
            }
        }
        return null; // No se encontró ningún usuario válido
    }



    ///
    /// Métodos de cuenta
    /// Sección de métodos de cuenta bla bla bla bla bla
    ///

    public static boolean eliminarCuenta(Usuario usuario, Cuenta cuenta) {
        int confirm = JOptionPane.showConfirmDialog(null,
                "¿Seguro que quieres eliminar esta cuenta?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            usuario.getCuentas().remove(cuenta);
            guardarUsuarios();
            JOptionPane.showMessageDialog(null, "Cuenta eliminada correctamente");
            return true;
        }
        return false;
    }


    public static boolean agregarCuenta(Usuario usuario, Cuenta.TipoCuenta tipo) {
        // Validar que no tenga una cuenta del mismo tipo
        if (usuario.getCuentas().stream().anyMatch(c -> c.getTipo().equals(tipo))) {
            JOptionPane.showMessageDialog(null, "Ya posee una cuenta de ese tipo");
            return false;
        }

        // Validar que no tenga más de 2 cuentas
        if (usuario.getCuentas().size() >= 2) {
            JOptionPane.showMessageDialog(null, "Límite de cuentas alcanzado");
            return false;
        }

        // Crear una nueva cuenta
        Cuenta nuevaCuenta = new Cuenta(tipo, 0.0);

        // Agregar la nueva cuenta al usuario
        usuario.getCuentas().add(nuevaCuenta);

        // Establecer la nueva cuenta como activa (última agregada)
        usuario.setCuentaActiva(usuario.getCuentas().size() - 1);

        guardarUsuarios();

        JOptionPane.showMessageDialog(null, "La nueva cuenta fue creada exitosamente");
        return true;

    }


    public static Cuenta buscarCuentaid(String numerodecuenta){
        for (Usuario usuario : usuarios) {//Busca en todos los usuarios cada múmero de cuenta
            for (Cuenta cuenta : usuario.getCuentas()) {
                if (cuenta.getNumerodecuenta().equals(numerodecuenta)) {
                    return cuenta;
                }
            }
        }
        return null;
    }

    ///
    /// Métodos de transacción
    /// Sección de métodos de transacción la maldita clase vacia (no cociné)
    ///


    public static boolean retirar(Usuario usuario, Cuenta cuenta, double monto) {//función pa retirar su nombre lo dice
        if (cuenta != null && monto > 0 && cuenta.getSaldo() >= monto) {
            cuenta.setSaldo(cuenta.getSaldo() - monto);

            Transaccion nueva = new Transaccion();
            nueva.setTipo(Transaccion.TipoTransaccion.RETIRO);
            nueva.setMonto(monto);
            nueva.setFechaHora(LocalDateTime.now());
            nueva.setEstado(Transaccion.EstadoTransaccion.COMPLETADA);
            nueva.setCuentaDestino("-");

            cuenta.getHistorial().add(nueva);
            usuario.getTransacciones().add(nueva);

            return true;
        }
        return false;
    }

    public static boolean depositar(Usuario usuario, Cuenta cuenta, double monto) {//deposita dinero a la cuenta .-.
        if (cuenta != null && monto > 0) {
            cuenta.setSaldo(cuenta.getSaldo() + monto);

            Transaccion nueva = new Transaccion();
            nueva.setTipo(Transaccion.TipoTransaccion.DEPOSITO);
            nueva.setMonto(monto);
            nueva.setFechaHora(LocalDateTime.now());
            nueva.setEstado(Transaccion.EstadoTransaccion.COMPLETADA);
            nueva.setCuentaDestino("-");

            cuenta.getHistorial().add(nueva);
            usuario.getTransacciones().add(nueva);

            return true;
        }
        return false;
    }


    public static boolean transferir(Usuario emisor, String cuentaOrigen, String cuentaDestino, double monto) {
        if (monto <= 0) {//transfiere dinero a otro usuario
            JOptionPane.showMessageDialog(null, "El monto debe ser mayor que 0");
            return false;
        }

        Cuenta origen = emisor.getCuentas().stream()
                .filter(c -> c.getNumerodecuenta().equals(cuentaOrigen))
                .findFirst()
                .orElse(null);

        if (origen == null) {
            JOptionPane.showMessageDialog(null, "Cuenta origen no encontrada");
            return false;
        }

        if (origen.getSaldo() < monto) {
            JOptionPane.showMessageDialog(null, "Saldo insuficiente");
            return false;
        }

        Cuenta destino = buscarCuentaid(cuentaDestino);
        if (destino == null) {
            JOptionPane.showMessageDialog(null, "Cuenta destino no encontrada");
            return false;
        }

        // Buscar usuario receptor
        Usuario receptor = usuarios.stream()
                .filter(u -> u.getCuentas().contains(destino))
                .findFirst()
                .orElse(null);

        if (receptor == null) {
            JOptionPane.showMessageDialog(null, "Usuario receptor no encontrado");
            return false;
        }

        // Realizar la transferencia
        origen.setSaldo(origen.getSaldo() - monto);
        destino.setSaldo(destino.getSaldo() + monto);

        // Transacción para emisor
        Transaccion transEmisor = new Transaccion(
                emisor.getId(),
                cuentaOrigen,
                cuentaDestino,
                Transaccion.TipoTransaccion.TRANSFERENCIA,
                monto,
                "Transferencia enviada"
        );
        transEmisor.setEstado(Transaccion.EstadoTransaccion.COMPLETADA);
        transEmisor.setFechaHora(LocalDateTime.now());

        // Transacción para receptor
        Transaccion transReceptor = new Transaccion(
                receptor.getId(),
                cuentaOrigen,
                cuentaDestino,
                Transaccion.TipoTransaccion.TRANSFERENCIA,
                monto,
                "Transferencia recibida"
        );
        transReceptor.setEstado(Transaccion.EstadoTransaccion.COMPLETADA);
        transReceptor.setFechaHora(LocalDateTime.now());

        // Guardar en historial de ambas cuentas
        origen.getHistorial().add(transEmisor);
        destino.getHistorial().add(transReceptor);

        // Guardar en historial de ambos usuarios
        emisor.getTransacciones().add(transEmisor);
        receptor.getTransacciones().add(transReceptor);

        guardarUsuarios();
        JOptionPane.showMessageDialog(null, "Transferencia realizada con éxito.");
        return true;
    }

    public static boolean pagarServicio(Usuario usuario, String numeroCuenta, double monto, String tipoServicio) {
        if (monto <= 0) {
            JOptionPane.showMessageDialog(null, "El monto debe ser mayor que 0");
            return false;
        }

        Cuenta cuenta = usuario.getCuentas().stream()
                .filter(c -> c.getNumerodecuenta().equals(numeroCuenta))
                .findFirst()
                .orElse(null);

        if (cuenta == null) {
            JOptionPane.showMessageDialog(null, "Cuenta no encontrada");
            return false;
        }

        if (cuenta.getSaldo() < monto) {
            JOptionPane.showMessageDialog(null, "Saldo insuficiente");
            return false;
        }

        cuenta.setSaldo(cuenta.getSaldo() - monto);

        Transaccion trans = new Transaccion(
                usuario.getId(),
                numeroCuenta,
                "-", // No hay cuenta destino
                Transaccion.TipoTransaccion.PAGO_SERVICIO,
                monto,
                "Pago de servicio: " + tipoServicio
        );
        trans.setId(usuario.getTransacciones().size() + 1);
        trans.completarTransaccion();

        usuario.agregarTransaccion(trans);
        cuenta.getHistorial().add(trans);

        guardarUsuarios();
        return true;
    }




}
