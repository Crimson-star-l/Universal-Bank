package Modelo;

import java.util.ArrayList;
import java.util.List;


public class Cuenta {
    private String numerodecuenta;
    private TipoCuenta tipo;
    private double saldo;
    private Boolean activa; //busca la cuenta aciva me ahorro detalles porque queda largo el texto
    private List<Transaccion> historial;

    public enum TipoCuenta {
        AHORRO,
        CORRIENTE
    }



    public Cuenta(TipoCuenta tipo,  double saldoInicial) {
        this.numerodecuenta = generarNumeroUnico();
        this.tipo = tipo;
        this.saldo = saldoInicial;
        this.activa = false;
        this.historial = new ArrayList<>();
    }




    private String generarNumeroUnico() {
        return "C" + System.currentTimeMillis(); //vamos ya sabemos que hace no voy a explicar denuevo .-.
    }

    public List<Transaccion> getHistorial() {return historial;}

    public String getNumerodecuenta() {return numerodecuenta;}

    public TipoCuenta getTipo() {return tipo;}
    public void setTipo(TipoCuenta tipo) {this.tipo = tipo;}

    public double getSaldo() {return saldo;}
    public void setSaldo(double saldo) {this.saldo = saldo;}

    public Boolean getActiva() {return activa;}
    public void setActiva(Boolean activa) {this.activa = activa;}

    public void agregarTransaccion(Transaccion t) {
        historial.add(t);
    }


    //para mostrar en gui
    @Override
    public String toString() {
        return tipo + " | Nº: " + numerodecuenta + " | Saldo: $" + saldo;
    }
}
