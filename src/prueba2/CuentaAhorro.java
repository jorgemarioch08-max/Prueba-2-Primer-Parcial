package prueba2;

import java.util.Calendar;

/**
 *
 * @author jorge
 */
public class CuentaAhorro extends CuentaBancaria {

    Calendar ultimoUso;

    public CuentaAhorro(int numero, String cliente, String moneda) {
        super(numero, cliente, moneda, TipoCuenta.AHORRO);
        ultimoUso = Calendar.getInstance();
    }

    private boolean estaActiva() {
        Calendar limite = Calendar.getInstance();
        limite.add(Calendar.MONTH, -6);

        return ultimoUso != null && !ultimoUso.before(limite);
    }

    public void depositar(double monto) {
        if (!estaActiva()) {
            throw new IllegalStateException("La cuenta está inactiva");
        }

        super.depositar(monto);
        ultimoUso = Calendar.getInstance();
    }

    public boolean retirar(double monto) {
        if (!estaActiva()) {
            throw new IllegalStateException("La cuenta está inactiva");
        }

        boolean resultado = super.retirar(monto);

        if (resultado) {
            ultimoUso = Calendar.getInstance();
        }

        return resultado;
    }

    public void quienSoy() {
        System.out.println("Soy una CuentaAhorro");
    }

    public String toString() {
        return super.toString()
                + "\nUltimo uso: " + ultimoUso.getTime();
    }
}