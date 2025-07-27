package Modelo;

import java.util.List;

public class Usuario {
    private int id;
    private String nombre;
    private String correo;
    private String clave;
    private String cedula;
    private String telefono;
    private String Fechadenacimiento;
    private List<Cuenta> cuentas;
    private List<Transaccion> transacciones;

    public String getCedula() {return cedula;}

    public String getCorreo() {return correo;}

    public int getId() {return id;}
}