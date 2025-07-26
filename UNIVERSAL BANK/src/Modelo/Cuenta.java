package Modelo;

public class Cuenta {
    private String numerodecuenta;
    private TipoCuenta tipo;
    private double saldo;
    private Boolean activa; //busca la cuenta aciva me ahorro detalles porque queda largo el texto


    public enum TipoCuenta {
        AHORRO,
        CORRIENTE
    }

    public Cuenta(TipoCuenta tipo,  double saldoInicial) {
        this.numerodecuenta = generarNumeroUnico();
        this.tipo = tipo;
        this.saldo = saldoInicial;
        this.activa = false;
    }

    private String generarNumeroUnico() {
        return "C" + System.currentTimeMillis(); //vamos ya sabemos que hace no voy a explicar denuevo .-.
    }

    public String getNumerodecuenta() {return numerodecuenta;}
    public TipoCuenta getTipo() {return tipo;}
    public double getSaldo() {return saldo;}
    public Boolean getActiva() {return activa;}
    public void setActiva(Boolean activa) {this.activa = activa;}
    public void setSaldo(double saldo) {this.saldo = saldo;}

    //para mostrar en gui
    @Override
    public String toString() {
        return tipo + " | Nº: " + numerodecuenta + " | Saldo: $" + saldo;
    }
}
