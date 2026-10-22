package prueba2;

import java.util.Calendar;

/**
 *
 * @author jorge
 */
public class CuentaPlazoFijo extends CuentaBancaria {

    Calendar findePlazo;
    double intereses;

    public CuentaPlazoFijo(int numero, String cliente, String moneda) {
        super(numero, cliente, moneda, TipoCuenta.PLAZO);
        findePlazo = null;
        intereses = 0;
    }

    public void setPlazo(int anio, int mes, int dia) {
        try {
            Calendar fecha = Calendar.getInstance();
            fecha.setLenient(false);
            fecha.set(anio, mes - 1, dia);
            fecha.getTime();

            findePlazo = fecha;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("La fecha no es valida");
        }
    }

    private boolean estaVencida() {
        if (findePlazo == null) {
            return false;
        }
        Calendar ahora = Calendar.getInstance();

        return !ahora.before(findePlazo);
    }

    @Override
    public boolean retirar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException(
                    "El monto a retirar debe ser mayor que 0"
            );
        }
        if (!estaVencida()) {
            if (monto > intereses) {
                return false;
            }

            intereses-=monto;

            registrarMovimiento(
                    "Retiro de intereses: " + monto + " - intereses: " + intereses
            );

            return true;
        }
        saldo+=intereses;
        intereses = 0;

        return super.retirar(monto);
    }

    @Override
    public void registrarIntereses() {
        if (!estaVencida()) {
            intereses+=saldo*tipo.tasa();
        } else {
            saldo+=intereses;
            intereses = 0;
        }
    }

    @Override
    public void reintegrar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException(
                    "El monto a reintegrar debe ser mayor que 0"
            );
        }
        if (!estaVencida()) {
            intereses+=monto;

            registrarMovimiento(
                    "Reversa: " + monto + " - intereses: " + intereses
            );
        } else {
            super.reintegrar(monto);
        }
    }

    public void quienSoy() {
        System.out.println("Soy una CuentaPlazoFijo");
    }

    public String toString() {
        return super.toString()
                + "\nFin de plazo: "
                + (findePlazo == null ? "No definido" : findePlazo.getTime())
                + "\nIntereses: " + intereses;
    }
}