package prueba2;

/**
 *
 * @author jorge
 */
public class Banco {

    CuentaBancaria[] cuentas = new CuentaBancaria[20];
    int cantidadCuentas = 0;

    public void agregarCuenta(CuentaBancaria c) {
        if (cantidadCuentas >= cuentas.length) {
            throw new IllegalStateException("El arreglo de cuentas está lleno");
        }

        verificarNumero(c.numero, 0);

        cuentas[cantidadCuentas] = c;
        cantidadCuentas++;
    }

    private void verificarNumero(int numero, int indice) {
        if (indice >= cantidadCuentas) {
            return;
        }

        if (cuentas[indice].numero == numero) {
            throw new IllegalArgumentException("El número de cuenta ya existe");
        }

        verificarNumero(numero, indice + 1);
    }

    public CuentaBancaria buscarPorNumero(int numero) {
        return buscarPorNumero(numero, 0);
    }

    private CuentaBancaria buscarPorNumero(int numero, int indice) {
        if (indice >= cantidadCuentas) {
            throw new IllegalArgumentException("No se encontró la cuenta");
        }

        if (cuentas[indice].numero == numero) {
            return cuentas[indice];
        }

        return buscarPorNumero(numero, indice + 1);
    }

    public void ordenarPorSaldo() {
        ordenarPorSaldo(0);
    }

    private void ordenarPorSaldo(int indice) {
        if (indice >= cantidadCuentas - 1) {
            return;
        }

        if (cuentas[indice].saldo > cuentas[indice + 1].saldo) {
            CuentaBancaria temporal = cuentas[indice];
            cuentas[indice] = cuentas[indice + 1];
            cuentas[indice + 1] = temporal;
        }

        ordenarPorSaldo(indice + 1);
    }

    public void transferir(int numeroOrigen, int numeroDestino, double monto) {
        if (numeroOrigen == numeroDestino) {
            throw new IllegalArgumentException(
                    "La cuenta de origen y destino deben ser diferentes"
            );
        }

        CuentaBancaria origen = buscarPorNumero(numeroOrigen);
        CuentaBancaria destino = buscarPorNumero(numeroDestino);

        boolean retiroExitoso = origen.retirar(monto);

        if (!retiroExitoso) {
            throw new IllegalStateException(
                    "Fondos insuficientes para realizar la transferencia"
            );
        }

        try {
            destino.depositar(monto);
        } catch (IllegalArgumentException | IllegalStateException e) {
            origen.reintegrar(monto);
            throw e;
        }
    }

    public void aplicarInteresesGlobal() {
        aplicarInteresesGlobal(0);
    }

    private void aplicarInteresesGlobal(int indice) {
        if (indice >= cantidadCuentas) {
            return;
        }

        try {
            cuentas[indice].registrarIntereses();
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println("Error en la cuenta "
                    + cuentas[indice].numero + ": " + e.getMessage());
        }

        aplicarInteresesGlobal(indice + 1);
    }

    public double saldoTotalDelBanco() {
        return saldoTotalDelBanco(0);
    }

    private double saldoTotalDelBanco(int indice) {
        if (indice >= cantidadCuentas) {
            return 0;
        }

        return cuentas[indice].saldo + saldoTotalDelBanco(indice + 1);
    }

    public void generarReporte() {
        ordenarPorSaldo();
        imprimirReporte(0);
    }

    private void imprimirReporte(int indice) {
        if (indice >= cantidadCuentas) {
            return;
        }

        System.out.println(cuentas[indice]);
        System.out.println();

        imprimirReporte(indice + 1);
    }
}