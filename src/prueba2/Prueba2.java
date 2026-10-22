package prueba2;

/**
 *
 * @author jorge
 */
import java.util.Scanner;

public class Prueba2 {

    public static Scanner sn = new Scanner(System.in);
    public static Banco banco = new Banco();
    public static int opc = -1;

    public static void menu() {

        if (opc == 9) {
            System.exit(0);
        }

        System.out.println("Banco Finanzas del Valle"
                + "\n"
                + "\nSeleccione una opción:"
                + "\n"
                + "\n1. Crear una cuenta."
                + "\n2. Deposito."
                + "\n3. Retiro."
                + "\n4. Transferencia."
                + "\n5. Aplicar intereses a todas las cuentas."
                + "\n6. Ver Reporte"
                + "\n7. Ver historial de una cuenta."
                + "\n8. Girar cheque."
                + "\n9. Salir."
                + "\n");

        opc = sn.nextInt();

        if (opc <= 0 || opc > 9) {
            System.out.println("\n"
                    + "Opción inválida, intentelo de nuevo"
                    + "\n");
        }

        switch (opc) {

            case 1:
                try {
                    System.out.println("Tipo de cuenta:");
                    System.out.println("1. Ahorro");
                    System.out.println("2. Cheques");
                    System.out.println("3. Plazo Fijo");
                    System.out.print("Seleccione: ");
                    int tipo = sn.nextInt();

                    System.out.print("Numero de cuenta: ");
                    int numero = sn.nextInt();

                    sn.nextLine();

                    System.out.print("Cliente: ");
                    String cliente = sn.nextLine();

                    System.out.print("Moneda: ");
                    String moneda = sn.nextLine();

                    if (tipo == 1) {

                        CuentaAhorro cuenta = new CuentaAhorro(
                                numero, cliente, moneda
                        );

                        banco.agregarCuenta(cuenta);

                        System.out.println("Cuenta de ahorro creada.");

                    } else if (tipo == 2) {

                        System.out.print("Limite de sobregiro: ");
                        double limite = sn.nextDouble();

                        CuentaCheques cuenta = new CuentaCheques(
                                numero, cliente, moneda, limite
                        );

                        banco.agregarCuenta(cuenta);

                        System.out.println("Cuenta de cheques creada.");

                    } else if (tipo == 3) {

                        CuentaPlazoFijo cuenta = new CuentaPlazoFijo(
                                numero, cliente, moneda
                        );

                        System.out.print("Anio de vencimiento: ");
                        int anio = sn.nextInt();

                        System.out.print("Mes de vencimiento: ");
                        int mes = sn.nextInt();

                        System.out.print("Dia de vencimiento: ");
                        int dia = sn.nextInt();

                        cuenta.setPlazo(anio, mes, dia);

                        banco.agregarCuenta(cuenta);

                        System.out.println("Cuenta de plazo fijo creada.");

                    } else {
                        System.out.println("Tipo de cuenta no valido.");
                    }

                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;

            case 2:
                try {
                    System.out.print("Numero de cuenta: ");
                    int numero = sn.nextInt();

                    System.out.print("Monto a depositar: ");
                    double monto = sn.nextDouble();

                    CuentaBancaria cuenta = banco.buscarPorNumero(numero);
                    cuenta.depositar(monto);

                    System.out.println("Deposito realizado correctamente.");

                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;

            case 3:
                try {
                    System.out.print("Numero de cuenta: ");
                    int numero = sn.nextInt();

                    System.out.print("Monto a retirar: ");
                    double monto = sn.nextDouble();

                    CuentaBancaria cuenta = banco.buscarPorNumero(numero);

                    if (cuenta.retirar(monto)) {
                        System.out.println("Retiro realizado correctamente.");
                    } else {
                        System.out.println("Fondos insuficientes.");
                    }

                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;

            case 4:
                try {
                    System.out.print("Numero de cuenta origen: ");
                    int origen = sn.nextInt();

                    System.out.print("Numero de cuenta destino: ");
                    int destino = sn.nextInt();

                    System.out.print("Monto a transferir: ");
                    double monto = sn.nextDouble();

                    banco.transferir(origen, destino, monto);

                    System.out.println("Transferencia realizada correctamente.");

                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;

            case 5:
                try {
                    banco.aplicarInteresesGlobal();
                    System.out.println("Intereses aplicados.");
                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;

            case 6:
                try {
                    banco.generarReporte();

                    System.out.println("Saldo total del banco: "
                            + banco.saldoTotalDelBanco());

                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;

            case 7:
                try {
                    System.out.print("Numero de cuenta: ");
                    int numero = sn.nextInt();

                    CuentaBancaria cuenta = banco.buscarPorNumero(numero);
                    cuenta.imprimirHistorial();

                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;

            case 8:
                try {
                    System.out.print("Numero de cuenta: ");
                    int numero = sn.nextInt();

                    CuentaBancaria cuenta = banco.buscarPorNumero(numero);

                    if (cuenta instanceof CuentaCheques) {

                        System.out.print("Monto del cheque: ");
                        double monto = sn.nextDouble();

                        CuentaCheques cheques = (CuentaCheques) cuenta;
                        cheques.girarCheque(monto);

                        System.out.println("Cheque girado correctamente.");

                    } else {
                        System.out.println("La cuenta no es de tipo Cheques.");
                    }

                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;

            case 9:
                System.out.println("Programa finalizado.");
                break;
        }

        menu();
    }

    public static void main(String[] args) {
        menu();
    }
}