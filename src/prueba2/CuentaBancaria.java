
package prueba2;
/**
 *
 * @author jorge
 */
import java.util.Calendar;
public class CuentaBancaria {
    
    //Atributos protegidos
    protected int numero;
    protected double saldo;
    protected String cliente;
    protected String moneda;
    protected TipoCuenta tipo;
    protected Calendar apertura;
    protected String[] historial = new String[50];
    protected int cantidadMovimientos = 0;

    public CuentaBancaria(int numero, String cliente, String moneda) {
        this.numero = numero;
        this.cliente = cliente;
        this.moneda = moneda;
        this.saldo = 0;
        this.apertura = Calendar.getInstance();
        this.tipo = TipoCuenta.CHEQUES;
    }

    public CuentaBancaria(int numero, String cliente, String moneda, TipoCuenta tipo) {
        this.numero = numero;
        this.cliente = cliente;
        this.moneda = moneda;
        this.saldo = 0;
        this.apertura = Calendar.getInstance();
        this.tipo = tipo;
    }

    public void depositar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a depositar debe ser mayor que 0");
        }
        saldo+=monto;

        registrarMovimiento(
                "Deposito: " + monto + " - saldo: " + saldo
        );
    }

    public boolean retirar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a retirar debe ser mayor que 0");
        }
        if (monto > saldo) {
            return false;
        }
        saldo -= monto;

        registrarMovimiento(
                "Deposito: " + monto + " - saldo: " + saldo
        );

        return true;
    }

    public void reintegrar(double monto) {
        saldo += monto;

        registrarMovimiento(
                "Reversa: " + monto + " - saldo: " + saldo
        );
    }

    public void registrarIntereses() {
        saldo+=saldo*tipo.tasa();
    }

    public void quienSoy() {
        System.out.println("CuentaBancaria (clase base)");
    }

    public void registrarMovimiento(String linea) {
        try {
            if (cantidadMovimientos >= historial.length) {
                throw new IllegalStateException(
                        "El historial de movimientos está lleno"
                );
            }

            historial[cantidadMovimientos] = linea;
            cantidadMovimientos++;

        } catch (IllegalStateException e) {
            System.out.println("Aviso: " + e.getMessage());
        }
    }

    public void imprimirHistorial() {
        for (int i = 0; i < cantidadMovimientos; i++) {
            System.out.println(historial[i]);
        }
    }

    public String toString() {
        return "Numero: " + numero
                + "\nCliente: " + cliente
                + "\nSaldo: " + saldo
                + "\nMoneda: " + moneda
                + "\nTipo: " + tipo
                + "\nApertura: " + apertura.getTime();
    }
}
