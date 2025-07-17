package Modelo;

import java.util.List;

public class Usuario {
    private int id;
    private String nombre;
    private String correo;
    private String clave;
    private int cedula;
    private int telefono;
    private String Fechadenacimiento;
    private List<Cuenta> cuentas;
    private List<Transaccion> transacciones;

    public int getId() {return id;}
}
