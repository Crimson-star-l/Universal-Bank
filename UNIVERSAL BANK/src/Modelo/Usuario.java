package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private int id;
    private String nombre;
    private String correo;
    private String clave;
    private String cedula;
    private String telefono;
    private String fechaDeNacimiento;
    private List<Cuenta> cuentas;
    private List<Transaccion> transacciones;


    public Usuario(int id, String nombre,String correo,String clave,String cedula,String telefono,String Fechadenacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
        this.cedula = cedula;
        this.telefono = telefono;
        this.fechaDeNacimiento = Fechadenacimiento;
        this.cuentas = new ArrayList<>();
        this.transacciones = new  ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo){ this.correo = correo; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getFechaDeNacimiento() { return fechaDeNacimiento; }
    public void setFechaDeNacimiento(String fechaDeNacimiento) {this.fechaDeNacimiento = fechaDeNacimiento; }

    public List<Cuenta> getCuentas() { return cuentas; }

    public void agregarCuenta(Cuenta cuenta) {
        cuentas.add(cuenta);
    }

    public Cuenta getCuentaActiva() {
        for (Cuenta c : cuentas) {
            if (c.getActiva() != null && c.getActiva()) {
                return c;
            }
        }
        return null;
    }

    public void cambiarCuentaActiva(String numeroCuenta) {
        for (Cuenta c : cuentas) {
            c.setActiva(c.getNumerodecuenta().equals(numeroCuenta));
        }
    }

    public void agregarTransaccion(Transaccion t) {
        transacciones.add(t);
    }

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }

    @Override
    public String toString() {
        return nombre + " (" + correo + ")";
    }

}