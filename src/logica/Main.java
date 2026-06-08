package logica; // Johao Santos - 22.004.848-9

import java.util.*;
import java.io.*;

public class Main {

	public static void main(String[] args) {
		SistemaMagos sistema = new SistemaMagos();
        sistema.cargaArchivo();

        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== MENÚ PRINCIPAL ===\n1. Panel Administrador\n2. Panel Analista\n3. Salir");
            System.out.print("Opción: ");
            int op = obtenerEntero(s);

            switch (op) {
                case 1 -> sistema.menuAdministrador();
                case 2 -> sistema.menuAnalisis();
                case 3 -> {
                    sistema.guardarDatos();
                    System.out.println("Datos guardados. ¡Hasta luego!");
                    return;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
	}
	private static int obtenerEntero(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Ingrese un número válido.");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

}
