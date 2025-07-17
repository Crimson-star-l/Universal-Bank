package Modelo;

public class Cuenta {
    String numerodecuenta; //Deberá generar un número al azar al crear una cuenta
    TipoCuenta cuenta;
    double saldo;
    String fechadecreacion; //esto probablemente lo quite a la mrd
    Boolean activa; //busca la cuenta aciva me ahorro detalles porque queda largo el texto


    enum TipoCuenta {
        AHORRO,
        CORRIENTE
        //No se cual es la otra
    }
}
