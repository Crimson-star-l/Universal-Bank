package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Cuenta {
    //me parece que ya explique que era un enum ehmm creo ?
    public enum TipoCuenta {
        AHORRO, CORRIENTE
    }

    private String numerodecuenta;
    private TipoCuenta tipo;
    private double saldo;
    private List<Transaccion> historial;

    public Cuenta(TipoCuenta tipo, double saldoInicial) {
        this.numerodecuenta = generarNumeroUnico();
        this.tipo = tipo;
        this.saldo = saldoInicial;
        this.historial = new ArrayList<>();
    }

    public Cuenta(TipoCuenta tipo) {
        this(tipo, 0.0);
    }

    private String generarNumeroUnico() {
        return "CUENTA-" + System.currentTimeMillis();  // Puedes mejorar esto si deseas.
    }

    public String getNumerodecuenta() {
        return numerodecuenta;
    }

    public TipoCuenta getTipo() {
        return tipo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public List<Transaccion> getHistorial() {
        return historial;
    }

    public void agregarTransaccion(Transaccion t) {
        historial.add(t);
    }

    @Override
    public String toString() {
        return tipo + " - " + numerodecuenta;
    }
}
