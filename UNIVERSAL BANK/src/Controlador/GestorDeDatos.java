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
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GestorDeDatos {
        private static final String USUARIOS_DATA = "src/Data/usuarios.json";
        private static List<Usuario> usuarios = new ArrayList<>();
        private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
        private static int proximoid=1;

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

            // Crear usuarios de ejemplo (opcional)
            usuarios = new ArrayList<>();
            guardarUsuarios();

            System.out.println("✓ Estructura inicial creada");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al crear estructura inicial: " + e.getMessage(),   "Error", JOptionPane.ERROR_MESSAGE );
        }
    }

}
