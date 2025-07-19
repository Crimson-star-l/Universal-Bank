package Controlador;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Modelo.Usuario;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.module.FindException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GestorDeDatos {
        private static final String USUARIOS_DATA = "src/Data/usuarios.json";
        private static List<Usuario> usuarios = new ArrayList<>();
        private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
        private static int proximoid=1;

        ///
        /// Métodos de carga de usuario
        ///  básicamente esta sección solo tiene funciones de cargar y guardar no voy a entrar en detalles solo vean el código .-.
        ///

    //Carga la base de datos de usuarios :b
    public static void CargarUsuario(){
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
                proximoid =usuarios.stream()
                        
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

            // Escribir JSON
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

    public static boolean crearUsuario(String nombre,String correo , int cedula,
                                       String clave, int telefono, String Fechadenacimiento) {

        ///NO TOCAR ESTA FUNCIÓN ///NO TOCAR ESTA FUNCIÓN
        ///NO TOCAR ESTA FUNCIÓN///NO TOCAR ESTA FUNCIÓN
        ///NO TOCAR ESTA FUNCIÓN ///NO TOCAR ESTA FUNCIÓN
        ///NO TOCAR ESTA FUNCIÓN ///NO TOCAR ESTA FUNCIÓN
        ///NO TOCAR ESTA FUNCIÓN ///NO TOCAR ESTA FUNCIÓN

        /// enserio no la toquen .-. hice algo que no estaba bien aquí y perdí el orden de algunas cosas

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

        /// Este monstruos es lo mismo que el de arriba
    }

    public static Usuario validarLogin(String correoOnombre, String clave) {
        for (Usuario usuario : usuarios) {//En pocas palabras recorre toda la lista de usuarios
            //Esta va pa el chris
            /*
             * En pocas palabras tienes que validar que el inicio de sesión sea correcto
             * más especificamenete que si se ingrese nombre o contraseña esten iguales a las de el usuario
             * pero la clave si tiene que ser la misma
             * */


            return usuario;//<---Esto no lo borres xd
        }
    }


    ///
    /// Métodos de cuenta
    /// Sección de métodos de cuenta bla bla bla bla bla
    ///

    //por aquí va una función pa agregar un tipo de cuenta nuevo


    //por aquí una de crear cuenta nueva

    //y una para buscar otras cuentas por id/número lo que sea



    ///
    /// Métodos de transacción
    /// Sección de métodos de transacción la maldita clase vacia (no cociné)
    ///



    ///
    /// Métodos utilitarios
    /// Sección de métodos de utilitarios (cosas pa admin y pruebas)
    ///


/*--------------------------------------------------------------------------------------------------------------------------------------------
* TODO
*  --Llenar la clase transacción, terminar las funciones de la clase controlador, dibujar los fondos de los paneles, buscar más información de la lógica de los bancos
*
*
* Como pueden ver adelante el proyecto lo más que pude queria adelantar más pero me detuve
* por un error tonto y ademas de que tuve que ver varios tutoriales con respecto a los archivos de tipo .json
* si les soy honesto no se del exactamente  si el programa compila hmmmm mejor dicho si compila más no se  si hace lo que quiero
* pero eso va a ser un problema que veré el domingo como saben no voy a poder estar presente mañana (el sabado)
* por lo que ustedes solo procuren hacer las cosas que les puse a hacer y si la tiene antes del domingo es mejor jejejejejejjajajajajja
*
*
*-------------------IMPORTANTE-------------------
*
* Como pueden ver esta clase tiene 2 errores no les hagan caso van a ser resueltos 1 de ellos por chris y el otro me encargo yo
*
*
* -------------------------------------------------MANDAMIENTOS------------------------------------------------
*hay 3 cosas que son muy importantes, tomenlo como mandamientos del proyecto
*
* 1.- NO TOCARE EL PAQUETE QUE DICE LIB NI SU CONTENIDO (LO PUEDEN VER PERO NO LE HAGAN NADA .-.)
* 2.- NO TOCARE LA FUNCIÓN crearUsuario() que está en la claseGestoDeDatos
* 3.- NO TRABAJARÉ EN LA CLASE GestorDeDatos SI NO SE ME PIDIÓ .-. (esta clase es muy volátil)
*
*--------------------------------------------------------------------------------------------------------------------------------------------
* */


}
