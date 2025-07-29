package Modelo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaccion {

    private int id;//código interno de la base de datos
    private int usuarioId;
    private String cuentaDeOrgingen;
    private String cuentaDestino;
    private TipoTransaccion tipo;
    double monto;
    private LocalDateTime fecha;
    private String descripcion;
    private EstadoTransaccion estado;
    private String referencia;


    //Enums para los estados y tipos de transacciones
    public enum TipoTransaccion{
        DEPOSITO,
        RETIRO,
        TRANSFERENCIA,
        PAGO_SERVICIO
    }

    public enum EstadoTransaccion{
        PENDIENTE,
        COMPLETADA,
        FALLIDA,
        CANCELADA
    }

    //Sobrecarga de constructores
    public Transaccion() {
        this.fecha = LocalDateTime.now();
        this.estado = EstadoTransaccion.PENDIENTE;
        this.referencia = generarReferencia();
    }

    public Transaccion(int usuarioId, String cuentaDeOrgingen, String cuentaDestino,
                       TipoTransaccion tipo, double monto, String descripcion) {
        this();
        this.usuarioId = usuarioId;
        this.cuentaDeOrgingen = cuentaDeOrgingen;
        this.cuentaDestino = cuentaDestino;
        this.tipo = tipo;
        this.monto = monto;
        this.descripcion = descripcion;
    }

    public Transaccion(int usuarioId, String cuentaDeOrgingen,TipoTransaccion tipo, double monto, String descripcion) {
        this(usuarioId,cuentaDeOrgingen,null,tipo,monto,descripcion);
    }

    ///
    ///    SECCIÓN DE MÉTODOS DE MOVIMIENTOS
    ///

    public boolean esTransferencia() {
        return tipo == TipoTransaccion.TRANSFERENCIA && cuentaDestino != null;
}

    public boolean moviminetopropio(){
        return tipo == TipoTransaccion.DEPOSITO || tipo == TipoTransaccion.RETIRO;
    }

    public void completarTransaccion(){
        this.estado = EstadoTransaccion.COMPLETADA;
    }

    public void fallarTransaccion(){
        this.estado = EstadoTransaccion.FALLIDA;
    }

    public void cancelarTransaccion(){
        this.estado = EstadoTransaccion.CANCELADA;
    }

    ///
    /// SECCIÓN DE MÉTODOS DE UTILIDAD
    ///

    /**
     * desglosando un poco el pequeño monstruo que está abajo se puede entender como:
     * 1.-genera un código de referencia con el texto TRX
     * 2.-currentTimeMillis() esto devuelve el tiempo actual del sistema en milisegundos y así generar un número único
     * 3.-(int) Math.random() genera un decimal random de tipo decimal por eso despues los casteamos a entero y eliminar el decimal
     * 4.- * 1000 nmms ahí mismo se entiende el número que sale del math.random se multiplica .-.
     * 5.-String.valueOf() convierte el número al azar en una cadena
     * y con esos pasos tenemos un número de referencia de la transacción únicos bastante interesante :b
     */
    private String generarReferencia() {
        return "TRX" + System.currentTimeMillis() + String.valueOf((int)(Math.random() * 1000));/// 0.5 * 1000 ->500.00 ->500 -> "500" -> TRX500
    }

    public String getFechaFormateada(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");/// 07/11/2025 08:27:34
        return fecha.format(formatter);
    }

    public String getResumenTransaccion() {
        StringBuilder resumen = new StringBuilder();
        resumen.append(tipo.toString())
                .append(" - $").append(String.format("%.2f", monto))  ///
                .append(" - ").append(getFechaFormateada())
                .append(" - ").append(estado.toString());
        return resumen.toString();
    }

    //validaciones
    public boolean montoValido() {
        return monto > 0;
    }

    public boolean tieneCuentaOrigen() {
        return cuentaDeOrgingen != null && !cuentaDeOrgingen.trim().isEmpty();
    }

    public boolean esTransferenciaValida() {
        if (tipo == TipoTransaccion.TRANSFERENCIA) {
            return tieneCuentaOrigen() &&
                    cuentaDestino != null &&
                    !cuentaDestino.trim().isEmpty() &&
                    !cuentaDeOrgingen.equals(cuentaDestino);
        }
        return true;
    }


    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public String getCuentaOrigen() { return cuentaDeOrgingen; }
    public void setCuentaOrigen(String cuentaOrigen) { this.cuentaDeOrgingen = cuentaDeOrgingen; }

    public String getCuentaDestino() { return cuentaDestino; }
    public void setCuentaDestino(String cuentaDestino) { this.cuentaDestino = cuentaDestino; }

    public TipoTransaccion getTipo() { return tipo; }
    public void setTipo(TipoTransaccion tipo) { this.tipo = tipo; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public LocalDateTime getFechaHora() { return fecha; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fecha = fecha; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public EstadoTransaccion getEstado() { return estado; }
    public void setEstado(EstadoTransaccion estado) { this.estado = estado; }

    public String getCodigoReferencia() { return referencia; }
    public void setCodigoReferencia(String codigoReferencia) {
        this.referencia = referencia;
    }

    @Override
    public String toString() {
        return "Transaccion{" +
                "id=" + id +
                ", tipo=" + tipo +
                ", monto=" + monto +
                ", fecha=" + getFechaFormateada() +
                ", estado=" + estado +
                ", referencia='" + referencia + '\'' +
                '}';
    }
}
